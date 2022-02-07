package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import com.ssafy.challympic.util.S3Uploader;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final MediaService mediaService;
    private final ChallengeService challengeService;
    private final PostLikeService postLikeService;
    private final UserService userService;
    private final TagService tagService;

    private final S3Uploader s3Uploader;

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


    // 프론트 단에서 전달받은 파일과 포스트 정보
    @Data
    @Getter @Setter
    @AllArgsConstructor
    static class PostRequest{
        private Integer user_no;
        private String post_content;
        private MultipartFile file;
    }


    @Data
    @AllArgsConstructor
    static class PostLikeUserDto{
        private int userNo;
        private String userName;
    }

    @Data
    @AllArgsConstructor
    static class PostDto{
        private int post_no;
        private int user_no;
        private String user_nickname;
        private String user_title;
        private int challenge_no;
        private int file_no;
        private String file_path;
        private String file_savedname;
        private String post_content;
        private int post_report;
        private Date post_regdate;
        private Date post_update;

        public PostDto(Post post) {
            this.post_no = post.getPost_no();
            this.user_no = post.getUser().getUser_no();
            this.user_nickname = post.getUser().getUser_nickname();
            this.user_title = post.getUser().getUser_title();
            this.challenge_no = post.getChallenge_no();
            this.file_no = post.getMedia().getFile_no();
            this.file_path = post.getMedia().getFile_path();
            this.file_savedname = post.getMedia().getFile_savedname();
            this.post_content = post.getPost_content();
            this.post_report = post.getPost_report();
            this.post_regdate = post.getPost_regdate();
            this.post_update = post.getPost_update();
        }
    }

    /**
     *  챌린지 번호로 포스트 가져오기(챌린지로 확인 예정
     *
     * */
    @GetMapping("/challenge/{challengeNo}/post")
    public Result list(@PathVariable("challengeNo") int challengeNo){
        Result result = null;

        // 포스트 리스트 뽑고
        List<Post> postList = postService.getPostList(challengeNo);
        List<PostDto> collect = postList.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        if(postList != null){
            result = new Result(true, HttpStatus.OK.value(), collect);
        } else {
            result = new Result(false, HttpStatus.OK.value());
        }

        return result;
    }

    /** 
     *  해당 게시글을 좋아요한 유저의 목록(Complete)
     *      - 유저의 정보를 모두 가져올 수도 있음
     *      - 유저 번호와 유저 닉네임만 전달받을 수도 있음
     *      - 현재 번호만 가져옴
     * */
    @GetMapping("/post/{postNo}/like")
    public Result likeList(@PathVariable("postNo") int postNo){

        // PostLike에서 게시글이 post인 것 추출

        // 받는 쪽에서 길이 구해서 좋아요 수 출력
        List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(postNo);

        log.info("size : " + postLikeList.size());

        // 좋아요 누른 유저 정보만 가져오기
        List<PostLikeUserDto> userList = new ArrayList<>();
        for(PostLike postLike : postLikeList){
            User user = userService.findUser(postLike.getUser_no());
            userList.add(new PostLikeUserDto(user.getUser_no(), user.getUser_nickname()));
        }

        return new Result(true, HttpStatus.OK.value(), userList);
    }
    
    /** 
     *  포스트 등록하기(Complete)
     *          alter table post
     *          convert to char set utf8;
     *      - Chaalenge : 챌린지 이름으로 검색
     *      - Challenge : 해당 챌린지 사진/영상 포맷 가져오기
     *      - File명 프론트 단에서 거르기
     *      - 확장자 프론트에서 거를지 백에서 거를지 결정
     *      - File 도메인 변경 및 테이블 명 변경 -> Media, File이라는 io의 객체와 이름이 겹침
     * */
    @PostMapping("/challenge/{challengeNo}/post")
    public Result create(@PathVariable("challengeNo") int challengeNo, PostRequest postRequest) throws IOException {

        log.info("Create Post");

        // 1. 플로우 시작
        Media media = null;
        MultipartFile files = null;

        try {

            files = postRequest.getFile();

            log.info(postRequest.getPost_content());

            // 확장자 체크
            String fileType = getFileType(files);

            if(fileType == null)
                // 지원하지 않는 확장자
                return new Result(false, HttpStatus.OK.value());

            // png/jpg, mp4 <- 확장자
            media = s3Uploader.upload(files, fileType.toLowerCase(), "media");

            if(media == null) {
                // AWS S3 업로드 실패
                return new Result(false, HttpStatus.OK.value());
            }

            int file_no = mediaService.saveMedia(media);


            // 본문 텍스트 파싱
            String content = postRequest.getPost_content().replaceAll("\"", "");
            String[] splitSharp = content.split(" ");

            for(String str : splitSharp){
                if(str.startsWith("#")){
                    // #을 분리하고 태그명만 추출
                    tagService.saveTag(str.substring(1));
                }
            }

            // 포스트 등록
            Post post = new Post();

            // 수정 필요
            post.setChallenge_no(challengeNo);  // 포스트가 속한 챌린지 정보

            post.setUser(userService.findUser(postRequest.getUser_no()));  // 포스트 작성 유저 정보
            post.setPost_content(postRequest.getPost_content());  // 포스트 본문
            post.setPost_report(0); // 신고횟수 초기값 0
            post.setMedia(media);

            int postId = postService.save(post);

            if(postId != -1)
                return new Result(true, HttpStatus.OK.value(), media);

        } catch(Exception e){
            e.printStackTrace();
        }

        return new Result(false, HttpStatus.OK.value());
    }
    
    /**
     *  기존 포스트를  수정하는 함수
     *      - Post를 postNo로 가져온다.
     *      - 파일 번호를 가져와서 파일을 업데이트
     *      - 포스트 업데이트
     * */
    @PutMapping("/challenge/{challengeNo}/post/{postNo}")
    public Result update(@PathVariable("challengeNo") int challengNo, @PathVariable("postNo") int postNo, PostRequest postRequest) throws Exception {

        Post _post = new Post();
        log.info("postNo : " + postNo);

        // 새로 저장
        if(postRequest.getFile() != null){

            // 기존 가지고 있던 데이터 삭제
            Post post = postService.getPost(postNo);
            mediaService.delete(post.getMedia().getFile_no());

            String type = getFileType(postRequest.getFile());
            Media media = s3Uploader.upload(postRequest.getFile(), type.toLowerCase(), "media");
            _post.setMedia(media);
        }

        if(postRequest.getPost_content() != null) {
            _post.setPost_content(postRequest.getPost_content());

            // 본문 텍스트 파싱
            String content = postRequest.getPost_content().replaceAll("\"", "");
            String[] splitSharp = content.split(" ");

            for(String str : splitSharp){
                if(str.startsWith("#")){
                    // #을 분리하고 태그명만 추출
                    tagService.saveTag(str.substring(1));
                }
            }
        }
        // 포스트 업데이트
        int postId = postService.update(postNo, _post);

        if(postId != 0)
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
     *  좋아요 클릭 처리(Complete)
     * */
    @PostMapping("/post/{postNo}/like/{userNo}")
    public Result like(@PathVariable("postNo") int postNo, @PathVariable("userNo") int userNo){

        // 포스트 라이크 테이블에서 해당 유저번호와 해당 게시글에 해당하는 엔티티가 있는지 검색
        List<PostLike> postLike = postLikeService.getPostLikeByUserNoPostNo(postNo, userNo);

        if(!postLike.isEmpty()){
            // 좋아요 누른 정보가 있으면
            // delete
            postLikeService.delete(postNo, userNo);
        } else {
            // insert
            PostLike _postLike = new PostLike(postNo, userNo);
            postLikeService.save(_postLike);
        }

        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * @param postNo 
     *   게시글 번호 받아서 해당 게시글의 신고 횟수 추가(Complete)
     */
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

        if(extension.equals("avi"))
            return "VIDEO";

        return null;
    }

    /** 
     *  챌린지 번호로 챌린지 정보를 가져와 파일 타입이 유효한 타입인지 확인
     *      - 유효하면 true
     *      - 잘못된 타입이면 false
     * */
    private boolean fileTypeValidate(int challengeNo, String fileType){
        // 챌린지 번호로 챌린지 정보 가져오기
        Challenge challenge = challengeService.findChallenges().get(challengeNo);

        //입력받은 파일의 타입과 챌린지 타입 비교
        if(fileType.equals(challenge.getChallenge_type())){
            return true;
        }
        return false;
    }

}
