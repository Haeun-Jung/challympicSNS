package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.FollowService;
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
        boolean follow = followService.follow(user_no, request.getFollow_follower_no());
        return new Result(true, HttpStatus.OK.value(), null,follow);
    }

    @GetMapping("/user/{userNo}/follower")
    public Result follower(@PathVariable("userNo") int user_no){
        List<User> follower = followService.following(user_no);
        List<FollowResponse> collect = follower.stream()
                .map(m ->
                    new FollowResponse(m)
                ).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @GetMapping("/user/{userNo}/following")
    public Result following(@PathVariable("userNo") int user_no){
        List<User> following = followService.follower(user_no);
        List<FollowResponse> collect = following.stream()
                .map(m ->
                        new FollowResponse(m)
                ).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @Data
    static class FollowRequest{
        private int follow_follower_no;
    }

    @Data
    @AllArgsConstructor
    static class FollowResponse{
        private int user_no;
        private String user_nickname;

        public FollowResponse(User user) {
            this.user_no = user.getUser_no();
            this.user_nickname = user.getUser_nickname();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;
        private boolean isFollowing;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }

        public Result(boolean isSuccess, int code, T data) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.data = data;
        }
    }
}
