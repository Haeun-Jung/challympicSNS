package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Alert;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.AlertService;
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
private final UserService userService;
private final AlertService alertService;

@PostMapping("/user/{userNo}/follow")
public Result follow(@PathVariable("userNo") int user_no, @RequestBody FollowRequest request){
    boolean follow = followService.follow(user_no, request.getFollow_follower_no());

    // 팔로우했을때 알림
    Alert alert = new Alert();
    User writer = userService.findUser(request.follow_follower_no);
    User follower = userService.findUser(user_no);
    alert.setUser(writer);
    alert.setAlert_content(follower.getUser_nickname() + "님이 팔로우합니다.");
    alertService.saveAlert(alert);

    return new Result(true, HttpStatus.OK.value(), null, follow);
}

@GetMapping("/{userNo}/follow/{followerNo}")
public Result isFollow(@PathVariable("userNo") int user_no, @PathVariable("followerNo") int follow_follower_no){
    if(user_no == follow_follower_no){
        return new Result(true, HttpStatus.OK.value(), null, false);
    }
    boolean follow = followService.isFollow(user_no, follow_follower_no);
    System.out.println(follow);
    System.out.println("=============================================================================");
    return new Result(true, HttpStatus.OK.value(), null, follow);
}

@GetMapping("/{userNo}/follow")
public Result followCnt(@PathVariable("userNo") int user_no){
    int followerCnt = followService.followingCnt(user_no); // 피드 주인을 팔로우한
    int followingCnt = followService.followerCnt(user_no); // 피드 주인이 팔로우한

    return new Result(true, HttpStatus.OK.value(), new FollowCnt(followerCnt, followingCnt));
}

@GetMapping("/{userNo}/follower/{loginUser}")
public Result follower(@PathVariable("userNo") int user_no, @PathVariable("loginUser") int login_user){
    List<User> follower = followService.following(user_no);
    List<FollowResponse> collect = follower.stream()
            .map(m ->{
                boolean follow = followService.isFollow(login_user, m.getUser_no());
                return new FollowResponse(m, follow);
            }).collect(Collectors.toList());
    return new Result(true, HttpStatus.OK.value(), collect);
}

@GetMapping("/{userNo}/following/{loginUser}")
public Result following(@PathVariable("userNo") int user_no, @PathVariable("loginUser") int login_user){
    List<User> following = followService.follower(user_no);
    List<FollowResponse> collect = following.stream()
            .map(m ->{
                boolean follow = followService.isFollow(login_user, m.getUser_no());
                return new FollowResponse(m, follow);
            }).collect(Collectors.toList());
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
    private String user_title;
    private boolean isFollow;
    private String user_profile;

    public FollowResponse(User user, boolean isFollow) {
        this.user_no = user.getUser_no();
        this.user_nickname = user.getUser_nickname();
        this.user_title = user.getUser_title();
        this.isFollow = isFollow;
        if(user.getMedia() != null){
            this.user_profile = user.getMedia().getFile_path()+"/"+user.getMedia().getFile_savedname();
        }
    }
}

@Data
@AllArgsConstructor
static class FollowCnt{
    private int followerCnt;
    private int followingCnt;
}

@Data
@AllArgsConstructor
static class Result<T>{
    private boolean isSuccess;
    private int code;
    private T data;
    private boolean isFollowing;
    private int cnt;

    public Result(boolean isSuccess, int code) {
        this.isSuccess = isSuccess;
        this.code = code;
    }

    public Result(boolean isSuccess, int code, T data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.data = data;
    }

    public Result(boolean isSuccess, int code, T data, boolean isFollowing) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.data = data;
        this.isFollowing = isFollowing;
    }
}
}