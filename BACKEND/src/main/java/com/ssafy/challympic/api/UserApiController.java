package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/user/account/{userNo}")
    public Result userInfo(@PathVariable("userNo") int user_no){
        User user = userService.userInfo(user_no);
        if(user != null) {
            return new Result(true, HttpStatus.OK.value(), new UserDto(user.getUser_no(), user.getUser_email(), user.getUser_nickname(), user.getUser_title()));
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value(), new UserDto());
        }
    }

    @PostMapping("/user/account")
    public Result login(@RequestBody loginUserRequest request){
        User user = userService.login(request.getUser_email(), request.getUser_pwd());
        if(user != null){
            return new Result(true, HttpStatus.OK.value(), new UserDto(user.getUser_no(), user.getUser_email(), user.getUser_nickname(), user.getUser_title()));
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value(), new UserDto());
        }
    }

    @PostMapping("/user/account/join")
    public Result join(@RequestBody User user){
        int join_no = userService.join(user);
        if(join_no > 0){
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(true, HttpStatus.BAD_REQUEST.value());
        }
    }

    @PutMapping("/user/account/{userNo}/nickname")
    public Result updateNickname(@PathVariable("userNo") int user_no, @RequestBody updateUserRequest request){
        userService.updateNickname(user_no, request.getUser_nickname());
        User user = userService.userInfo(user_no);
        if(user != null) {
            return new Result(true, HttpStatus.OK.value(), new UserDto(user.getUser_no(), user.getUser_email(), user.getUser_nickname(), user.getUser_title()));
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value(), new UserDto());
        }
    }

    @PutMapping("/user/account/{userNo}/pwd")
    public Result updatePwd(@PathVariable("userNo") int user_no, @RequestBody updateUserRequest request){
        User findUser = userService.userInfo(user_no);
        if(!findUser.getUser_pwd().equals(request.getUser_pwd())){
            return new Result(false, HttpStatus.BAD_REQUEST.value(), new UserDto());
        }
        userService.updatePwd(user_no, request.getUser_newpwd());
        User user = userService.userInfo(user_no);
        if(user != null) {
            return new Result(true, HttpStatus.OK.value(), new UserDto(user.getUser_no(), user.getUser_email(), user.getUser_nickname(), user.getUser_title()));
        }else{
            System.out.println("엥..?");
            return new Result(false, HttpStatus.NO_CONTENT.value(), new UserDto());
        }
    }

    @DeleteMapping("/user/account/{userNo}")
    public Result deleteUser(@PathVariable("userNo") int user_no){
        userService.deleteUser(user_no);
        User user = userService.userInfo(user_no);
        if(user == null) {
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value());
        }
    }

    @GetMapping("/user/account/confirm/email")
    public Result confirmEmail(@RequestBody confirmUserRequest request){
        boolean result = userService.validateDuplicateEmail(request.getUser_email());
        if(result) {
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value());
        }
    }

    @GetMapping("/user/account/confirm/nickname")
    public Result confirmNickname(@RequestBody confirmUserRequest request){
        boolean result = userService.validateDuplicateNickname(request.getUser_nickname());
        if(result) {
            return new Result(true, HttpStatus.OK.value());
        }else{
            return new Result(false, HttpStatus.NO_CONTENT.value());
        }
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
    }

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
}