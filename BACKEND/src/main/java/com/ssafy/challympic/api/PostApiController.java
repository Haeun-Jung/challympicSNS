package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import com.ssafy.challympic.util.MD5Generator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.parameters.P;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;


@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final MediaService mediaService;
    private final ChallengeService challengeService;
    private final PostLikeService postLikeService;
    private final UserService userService;
//    private final TagService tagService;

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }
    }

    // 프론트 단에서 전달받을 포스트 정보
    @Data
    @AllArgsConstructor
    static class PostForm{
        private int user_no;
        private String post_content;
    }

    /**
     *  챌린지 번호로 포스트 가져오기
     *
     * */
    /*
    @GetMapping("/challenge/{challengeNo}/post")
    public Result list(@PathVariable("challengeNo") int challengeNo){
        Result result = null;

        List<Post> postList = postService.getPostList(challengeNo);

        if(postList != null){
            result = new Result(true, HttpStatus.OK.value(), postList);
        } else {
            result = new Result(false, HttpStatus.OK.value());
        }

        return result;
    }
     */
    
    /** 
     *  포스트 등록하기
     *      - Chaalenge : 챌린지 이름으로 검색
     *      - Challenge : 해당 챌린지 사진/영상 포맷 가져오기
     *      - File명 프론트 단에서 거르기
     *      - 확장자 프론트에서 거를지 백에서 거를지 결정
     *      - File 도메인 변경 및 테이블 명 변경 -> Media, File이라는 io의 객체와 이름이 겹침
     * */
    @PostMapping("/challenge/{challengeNo}/post")
    public Result create(@PathVariable("challengeNo") int challengeNo, @RequestParam("file") MultipartFile files, @RequestBody PostForm postForm) throws IOException {
//    public ResponseEntity<InputStreamResource> create(@PathVariable("challengeNo") int challengeNo) throws IOException {

//        // 파일 스트리밍 테스트
//        File file = new File("C:\\SSAFY\\WorkSpace_for_Git\\01_Common_Sub_2\\BACKEND\\public\\work\\Tomcat\\localhost\\challympic\\2022.02.03\\seoul.mp4");
//        InputStream inputStream = new FileInputStream("C:\\SSAFY\\WorkSpace_for_Git\\01_Common_Sub_2\\BACKEND\\public\\work\\Tomcat\\localhost\\challympic\\2022.02.03\\seoul.mp4");
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Accept-Ranges", "bytes");
//        headers.set("Content-Type", "video/mp4");
//        headers.set("Content-Range", "bytes 50-1025/17839845");
//        headers.set("Content-Length", String.valueOf(file.length()));
//
//        return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);

        // 확장자 체크
        String fileType = getFileType(files);

//        if(fileType == null || !fileTypeValidate(challengeNo, fileType))
//            return new Result(false, HttpStatus.OK.value());

        Media media = fileToMedia(files);
        if(media == null)
            return new Result(false, HttpStatus.OK.value());

        Long file_no = mediaService.saveMedia(media);
        // 파일 저장 끝

        // 본문 텍스트 파싱
        String content = postForm.getPost_content();
        String[] splitSharp = content.split("#");
        List<String> list = new ArrayList<>();
        for(String str : splitSharp){
            list.add(str.split(" ")[0]);
        }

//            for(String str : list){
//                tag.save(str);
//            }

        // 포스트 등록
        Post post = new Post();
//        post.setChallenge(challengeService.findChallenges().get(challengeNo));
//        post.setUser_no(postForm.getUser_no());
//        post.setPost_content(content);
        post.setPost_report(0);

        int postId = postService.save(post);


        // ---------------------------------------------------------------------------------

        if(postId != -1)
            return new Result(true, HttpStatus.OK.value(), media);
        else
            return new Result(false, HttpStatus.OK.value());

    }
    
    
    /** 
     *  기존 포스트를  수정하는 함수
     *      - Post를 postNo로 가져온다.
     *      - 파일 번호를 가져와서 파일을 업데이트
     *      - 포스트 업데이트
     * */
    @PutMapping("/challenge/{challengeNo}/post/{postNo}")
    public Result update(@PathVariable("challengeNo") int challengNo, @PathVariable("postNo") int postNo, @RequestParam("file") MultipartFile files
            , @RequestBody PostForm postForm){

        Post post = postService.getPost(postNo);

        // 파일 업데이트, 파일 저장도 여기서 이루어짐
        Long fileId = mediaService.update(post.getMedia().getFile_no(), fileToMedia(files));

        Post _post = new Post();
        _post.setPost_content(postForm.getPost_content());

        // 포스트 업데이트
        int postId = postService.update(postNo, _post);
        
        if(fileId == null || postId != 0)
            return new Result(true, HttpStatus.OK.value());
        
        return new Result(false, HttpStatus.OK.value());
    }
    
    
    /** 
     *  저장된 포스트를 삭제하는 함수
     * */
    @DeleteMapping("/post/{postNo}")
    public Result delete(@PathVariable("postNo") int postNo){

        Post post = postService.getPost(postNo);

        mediaService.delete(post.getMedia().getFile_no());

        postService.delete(postNo);

        return new Result(true, HttpStatus.OK.value());
    }

    /**
     *  좋아요 클릭 처리
     * */
    @PostMapping("/post/{postNo}/like/{userNo}")
    public Result like(@PathVariable("postNo") int postNo, @PathVariable("userNo") int userNo){

        Post post = postService.getPost(postNo);
        Long likeNo = post.getPostLike().getLike_no();

        // 포스트 라이크 테이블에서 해당 유저번호와 해당 게시글에 해당하는 엔티티가 있는지 검색
        PostLike postLike = postLikeService.getPostLikeByUserNoPostNo(postNo, userNo);

        if(postLike != null){
            // delete
            postLikeService.delete(postNo, userNo);
        } else {
            // insert
            PostLike _postLike = new PostLike(postService.getPost(postNo), userService.findUser(userNo));
            postLikeService.save(_postLike);
        }

        return new Result(true, HttpStatus.OK.value());
    }

    @PostMapping("/report/post/{postNo}")
    public Result report(@PathVariable("postNo") int postNo){

        postService.updateReport(postNo);

        return new Result(true, HttpStatus.OK.value());
    }

    /** 
     *  프론트 단에서 파일을 받아 확장자에 따라 파일 타입을 결정
     *      - String으로 할지 Enum으로 할지 결정 필요
     * */
    private String getFileType(MultipartFile files){
        String fileName = files.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if(extension.equals("mp4") || extension.equals("MP4"))
            return "VIDEO";

        extension = extension.toLowerCase();
        if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png"))
            return "IMAGE";

        if(extension.equals("AVI"))
            return "VIDEO";

        return null;
    }

    /** 
     *  챌린지 번호로 챌린지 정보를 가져와 파일 타입이 유효한 타입인지 확인
     *      - 유효하면 true
     *      - 잘못된 타입이면 false
     * */
//    private boolean fileTypeValidate(int challengeNo, String fileType){
//        // 챌린지 번호로 챌린지 정보 가져오기
////        Challenge challenge = challengeService.
//
//        // 임시 코드
//        Challenge challenge = new Challenge();
//
          // 입력받은 파일의 타입과 챌린지 타입 비교
//        if(fileType.equals(challenge.getChallenge_type())){
//            return true;
//        }
//        return false;
//    }

    /** 
     *  프론트에서 전달 받은 파일을 로컬 서버에 저장하고 Media 엔티티로 변환
     * */
    private Media fileToMedia(MultipartFile files){

        try {
            // 파일 저장 시작
            // 실제 파일명
            String originFileName = files.getOriginalFilename();
            // 저장 파일명
            String savedFileName = new MD5Generator(originFileName).toString();
            // 실제 저장 경로
            SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");

//            String path = System.getProperty("user.dir") + "\\files\\" + date.format(new Date());
//            String path = System.getProperty("user.dir") + "\\files";
            String path = System.getProperty("user.dir") + "\\public\\work\\Tomcat\\localhost\\challympic";

//            // 저장 경로에 해당하는 폴더가 없으면 폴더 생성(files)
//            if (!new File(path).exists()) {
//                try {
//                    new File(path).mkdir();
//                } catch (Exception e) {
//                    e.getStackTrace();
//                }
//            }

            // 업로드일에 해당하는 날짜
            path += "\\" + date.format(new Date());
            
            if (!new File(path).exists()) {
                try {
                    new File(path).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String savedPath = path + "\\" + savedFileName;
            files.transferTo(new File(savedPath));

            return new Media(originFileName, savedFileName, path);
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
