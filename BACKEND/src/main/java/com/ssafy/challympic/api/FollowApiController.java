package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Follow;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.FollowService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FollowApiController {

    private final FollowService followService;

    @PostMapping("/user/{userNo}/follow")
    public Result follow(@PathVariable("userNo") int user_no, @RequestBody FollowRequest request){
        followService.follow(user_no, request.getFollow_follower_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/user/{userNo}/follower")
    public Result follower(@PathVariable("userNo") int user_no){
        List<User> follower = followService.following(user_no);
        List<UserResponse> collect = follower.stream()
                .map(m ->
                    new UserResponse(m.getUser_no(), m.getUser_nickname())
                ).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @GetMapping("/user/{userNo}/following")
    public Result following(@PathVariable("userNo") int user_no){
        List<User> following = followService.follower(user_no);
        List<UserResponse> collect = following.stream()
                .map(m ->
                        new UserResponse(m.getUser_no(), m.getUser_nickname())
                ).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @Data
    static class FollowRequest{
        private int follow_follower_no;
    }

    @Data
    @AllArgsConstructor
    static class UserResponse{
        private int user_no;
        private String user_nickname;
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
