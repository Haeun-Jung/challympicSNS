package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.service.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;
    private final CommentLikeService commentLikeService;
    private final CommentService commentService;
    private final PostService postService;
    private final ChallengeService challengeService;

    @GetMapping("/admin/users")
    public Result userList(){
        List<User> userList = adminService.userList();
        List<UserDto> collect = userList.stream()
                .map(u -> {
                    UserDto userDto = new UserDto(u);
                    int post_report = postService.postReportCntByUser(u.getUser_no());
                    int challenge_report = challengeService.challengeReportCntByUser(u.getUser_no());
                    int comment_report = commentService.commentReportCntByUser(u.getUser_no());
                    userDto.setReport(post_report+challenge_report+comment_report);
                    return userDto;
                })
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @PutMapping("/admin/users")
    public Result inactiveUser(@RequestBody UserRequest userRequest){
        adminService.updateUserActive(userRequest.user_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @DeleteMapping("/admin/users")
    public Result deleteUser(@RequestBody UserRequest userRequest){
        adminService.deleteUser(userRequest.user_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/admin/challenges")
    public Result challengeList(){
        List<Challenge> challengeList = adminService.challengeList();
        List<ChallengeDto> collect = challengeList.stream()
                .map(c -> {
                    ChallengeDto challengeDto = new ChallengeDto(c);
                    int post_cnt = postService.postCntByChallenge(c.getChallenge_no());
                    challengeDto.setPost_cnt(post_cnt);
                    challengeDto.setInterest_cnt(0);
                    return challengeDto;
                }).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @DeleteMapping("/admin/challenges")
    public Result deleteChallenge(@RequestBody ChallengeRequest challengeRequest){
        adminService.deleteChallenge(challengeRequest.challenge_no);
        // 하위 포스트 삭제
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/admin/comments")
    public Result commentList(){
        List<Comment> commentList = adminService.commentList();
        List<CommentDto> collect = commentList.stream()
                .map(c -> {
                    CommentDto commentDto = new CommentDto(c);
                    int like_cnt = commentLikeService.findLikeCntByComment(c.getComment_no());
                    commentDto.setLike_cnt(like_cnt);
                    return commentDto;
                }).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @DeleteMapping("/admin/comments")
    public Result deleteComment(@RequestBody CommentRequest commentRequest){
        adminService.deleteComment(commentRequest.comment_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @Data
    static class CommentRequest{
        private int comment_no;
    }

    @Data
    static class ChallengeRequest{
        private int challenge_no;
    }

    @Data
    static class UserRequest{
        private int user_no;
    }

    @Data
    static class CommentDto{
        private int comment_no;
        private String comment_content;
        private int like_cnt;
        private Date comment_regdate;
        private int comment_report;

        public CommentDto(Comment comment) {
            this.comment_no = comment.getComment_no();
            this.comment_content = comment.getComment_content();
            this.comment_regdate = comment.getComment_regdate();
            this.comment_report = comment.getComment_report();
        }
    }

    @Data
    static class ChallengeDto{
        private int challenge_no;
        private String challenge_title;
        private String user_email;
        private int post_cnt;
        private int interest_cnt;
        private Date challenge_start;
        private Date challenge_end;
        private boolean challenge_official;
        private int challenge_report;

        public ChallengeDto(Challenge challenge) {
            this.challenge_no = challenge.getChallenge_no();
            this.challenge_title = challenge.getChallenge_title();
            this.user_email = challenge.getUser().getUser_email();
            this.challenge_start = challenge.getChallenge_start();
            this.challenge_end = challenge.getChallenge_end();
            this.challenge_official = challenge.isChallenge_official();
            this.challenge_report = challenge.getChallenge_report();
        }
    }

    @Data
    static class UserDto{
        private int user_no;
        private String user_email;
        private String user_nickname;
        private UserActive user_active;
        private int report;

        public UserDto(User user) {
            this.user_no = user.getUser_no();
            this.user_email = user.getUser_email();
            this.user_nickname = user.getUser_nickname();
            this.user_active = user.getUser_active();
        }
    }
}
