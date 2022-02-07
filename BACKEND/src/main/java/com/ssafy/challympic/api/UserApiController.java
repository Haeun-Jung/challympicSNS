package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.MediaService;
import com.ssafy.challympic.service.TitleService;
import com.ssafy.challympic.service.UserAuthService;
import com.ssafy.challympic.service.UserService;
import com.ssafy.challympic.util.MD5Generator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final UserAuthService userAuthService;
    private final MediaService mediaService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TitleService titleService;

    @GetMapping("/user/account/{userNo}")
    public Result findUser(@PathVariable("userNo") int user_no){
        User findUser = userService.findUser(user_no);
        List<Title> titles = titleService.findTitlesByUserNo(user_no);

        Map<String, Object> data = new HashMap<>();
        data.put("user", findUser);
        data.put("titleList", titles);
        if(findUser != null) {
            return new Result(true, HttpStatus.OK.value(), data);
        }else{
            return new Result(false, HttpStatus.BAD_REQUEST.value(), new UserDto());
        }
    }

    @PostMapping("/join")
    public Result join(@RequestBody joinUserRequest request){
        System.out.println("test");
        User newUser = new User();

        newUser.setUser_email(request.getUser_email());
        newUser.setUser_nickname(request.getUser_nickname());
        String rawPwd = request.getUser_pwd();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);

        // userAuth에 저장
        UserAuth newUserAuth = new UserAuth();
        newUserAuth.setUser_email(request.getUser_email());
        newUserAuth.setUser_pwd(encPwd);

        int join_no = userService.join(newUser);
        userAuthService.join(newUserAuth);
        if(join_no > 0){
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(true, HttpStatus.BAD_REQUEST.value());
        }
    }

    /**
     * 파일 수정 추가 필요
     */
    @PutMapping("/user/account/{userNo}")
    public Result updateUser(@PathVariable("userNo") int user_no, @RequestBody updateUserRequest request, @RequestParam("file") MultipartFile files){

        // 확장자 체크
        String fileType = getFileType(files);

        Media media = fileToMedia(files);
        if(media == null)
            return new Result(false, HttpStatus.OK.value());

        int file_no = mediaService.saveMedia(media);

//        Media findMedia = mediaService.

        userService.updateUser(user_no, request.getUser_nickname());
        User user = userService.findUser(user_no);
        if(user != null) {
            return new Result(true, HttpStatus.OK.value(), new UserDto(user));
        }else{
            return new Result(false, HttpStatus.BAD_REQUEST.value(), new UserDto());
        }
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
            String path = System.getProperty("user.dir") + "\\files";

            // 저장 경로에 해당하는 폴더가 없으면 폴더 생성(files)
            if (!new File(path).exists()) {
                try {
                    new File(path).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

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

    @PutMapping("/user/account/{userNo}/pwd")
    public Result updatePwd(@PathVariable("userNo") int user_no, @RequestBody updateUserRequest request){
        UserAuth findUser = userAuthService.findUser(user_no);
        System.out.println(bCryptPasswordEncoder.matches(request.getUser_pwd(), findUser.getUser_pwd()));
        if(!bCryptPasswordEncoder.matches(request.getUser_pwd(), findUser.getUser_pwd())){
            return new Result(false, HttpStatus.BAD_REQUEST.value(), new UserDto());
        }

        String newpwd = bCryptPasswordEncoder.encode(request.getUser_newpwd());
        System.out.println(newpwd);
        userAuthService.updatePwd(user_no, newpwd);
        User user = userService.findUser(user_no);
        if(user != null) {
            return new Result(true, HttpStatus.OK.value(), new UserDto(user));
        }else{
            return new Result(false, HttpStatus.BAD_REQUEST.value(), new UserDto());
        }
    }

    //TODO : User, UserAuth 다 같이 지워줘야할지?
    @DeleteMapping("/user/account/{userNo}")
    public Result deleteUser(@PathVariable("userNo") int user_no){
        userService.deleteUser(user_no);
        User user = userService.findUser(user_no);
        if(user == null) {
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PostMapping("/confirm/email")
    public Result confirmEmail(@RequestBody confirmUserRequest request){
        boolean result = userService.validateDuplicateEmail(request.getUser_email());
        if(result) {
            return new Result(true, HttpStatus.OK.value(), true);
        }else{
            return new Result(true, HttpStatus.OK.value(), false);
        }
    }

    @PostMapping("/confirm/nickname")
    public Result confirmNickname(@RequestBody confirmUserRequest request){
        boolean result = userService.validateDuplicateNickname(request.getUser_nickname());
        if(result) {
            return new Result(true, HttpStatus.OK.value(), true);
        }else{
            return new Result(true, HttpStatus.OK.value(), false);
        }
    }

    @Data
    static class joinUserRequest{
        private String user_email;
        private String user_pwd;
        private String user_nickname;
    }

    @Data
    static class confirmUserRequest{
        private String user_email;
        private String user_nickname;
    }

    @Data
    static class updateUserRequest{
        private String user_nickname;
        private String user_pwd;
        private String user_newpwd;
    }

    @Data
    static class loginUserRequest{
        private String user_email;
        private String user_pwd;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UserDto{
        private int user_no;
        private String user_email;
        private String user_nickname;
        private String user_title;

        public UserDto(User user) {
            this.user_no = user.getUser_no();
            this.user_email = user.getUser_email();
            this.user_nickname = user.getUser_nickname();
            this.user_title = user.getUser_title();
        }
    }
}