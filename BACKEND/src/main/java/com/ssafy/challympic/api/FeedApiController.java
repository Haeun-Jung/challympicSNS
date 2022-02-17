package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

    private final PostService postService;
    private final ChallengeService challengeService;
    private final PostLikeService postLikeService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 내가 만든 챌린지 목록
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/challenge")
    public Result getChallengeList(@PathVariable int userNo) {
        List<Challenge> challenges = challengeService.getChallengeByUserNo(userNo);
        List<ChallengeResponse> collect = challenges.stream()
                .map(c -> {
                    List<Post> postList = postService.getPostList(c.getChallenge_no());
                    Post firstPost = postList.get(0);
                    int post_cnt = challengeService.findPostCnt(c.getChallenge_no());
                    int subscription_cnt = challengeService.findSubscriptionCnt(c.getChallenge_no());
                    return new ChallengeResponse(firstPost, c, post_cnt, subscription_cnt);
                })
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value() , collect);
    }

    @Data
    static class ChallengeResponse{
        private int challenge_no;
        private boolean isVideo;
        private int post_no;
        private int file_no;
        private String file_path;
        private String file_savedname;
        private String challenge_title;
        private int post_cnt;
        private int subscription_cnt;

        public ChallengeResponse(Post post, Challenge challenge, int post_cnt, int subscription_cnt) {
            this.challenge_no = post.getChallenge_no();
            if(challenge.getChallenge_type() == ChallengeType.VIDEO) this.isVideo = true;
            this.post_no = post.getPost_no();
            this.file_no = post.getMedia().getFile_no();
            this.file_path = post.getMedia().getFile_path();
            this.file_savedname = post.getMedia().getFile_savedname();
            this.challenge_title = challenge.getChallenge_title();
            this.post_cnt = post_cnt;
            this.subscription_cnt = subscription_cnt;
        }
    }

    @Data
    static class PostResponse{
        private int challenge_no;
        private boolean isVideo;
        private int post_no;
        private int file_no;
        private String file_path;
        private String file_savedname;
        private String challenge_title;
        private int like_cnt; //
        private int comment_cnt;

        public PostResponse(Post post, Challenge challenge, int like_cnt, int comment_cnt) {
            this.challenge_no = post.getChallenge_no();
            if(challenge.getChallenge_type() == ChallengeType.VIDEO) this.isVideo = true;
            this.post_no = post.getPost_no();
            this.file_no = post.getMedia().getFile_no();
            this.file_path = post.getMedia().getFile_path();
            this.file_savedname = post.getMedia().getFile_savedname();
            this.challenge_title = challenge.getChallenge_title();
            this.like_cnt = like_cnt;
            this.comment_cnt = comment_cnt;
        }
    }

    /**
     * 구독한 챌린지 목록
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/subscription")
    public Result getSubscriptionChallengeList(@PathVariable int userNo) {
        List<Challenge> challenges = challengeService.findChallengeBySubscription(userNo);
        if(challenges == null) return new Result(false, HttpStatus.NOT_FOUND.value());
        List<ChallengeResponse> collect = challenges.stream()
                .map(c -> {
                    List<Post> postList = postService.getPostList(c.getChallenge_no());
                    Post firstPost = postList.get(0);
                    int post_cnt = challengeService.findPostCnt(c.getChallenge_no());
                    int subscription_cnt = challengeService.findSubscriptionCnt(c.getChallenge_no());
                    return new ChallengeResponse(firstPost, c, post_cnt, subscription_cnt);
                })
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    /**
     * 내가 좋아요한 포스트 목록
     */
    @GetMapping("/feed/{userNo}/like")
    public Result getLikePostList(@PathVariable int userNo) {
        List<Post> posts = postService.getLikePostListByUserNo(userNo);
        List<PostResponse> collect = new ArrayList<>();
        if(!posts.isEmpty()){
            collect = posts.stream()
                    .map(p -> {
                        int challenge_no = p.getChallenge_no();
                        Challenge challenge = challengeService.findChallengeByChallengeNo(challenge_no);
                        int like_cnt = postLikeService.postLikeCnt(p.getPost_no());
                        int comment_cnt = commentService.postCommentCnt(p.getPost_no());
                        return new PostResponse(p, challenge, like_cnt, comment_cnt);
                    }).collect(Collectors.toList());
        }
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    /**
     * 내가 만든 포스트 목록
     */
    @GetMapping("/feed/{userNo}/post")
    public Result postByUser(@PathVariable("userNo") int user_no){
        List<Post> postListByUserNo = postService.getPostListByUserNo(user_no);
        List<PostResponse> collect = new ArrayList<>();
        if(!postListByUserNo.isEmpty()){
            collect = postListByUserNo.stream()
                    .map(p -> {
                        int challenge_no = p.getChallenge_no();
                        Challenge challenge = challengeService.findChallengeByChallengeNo(challenge_no);
                        int like_cnt = postLikeService.postLikeCnt(p.getPost_no());
                        int comment_cnt = commentService.postCommentCnt(p.getPost_no());
                        return new PostResponse(p, challenge, like_cnt, comment_cnt);
                    }).collect(Collectors.toList());
        }
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @GetMapping("/feed/{userNo}")
    public Result getFeedUserInfo(@PathVariable("userNo") int user_no){
        if(user_no == 0){
            return new Result(false, HttpStatus.NO_CONTENT.value());
        }
        User findUser = userService.findUser(user_no);
        if(findUser == null){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }else{
            return new Result(true, HttpStatus.OK.value(), new UserDto(findUser));
        }
    }

    @Data
    static class UserDto{
        private int user_no;
        private String user_nickname;
        private String user_title;
        private int file_no;
        private String file_path;
        private String file_savedname;

        public UserDto(User user) {
            this.user_no = user.getUser_no();
            this.user_nickname = user.getUser_nickname();
            this.user_title = user.getUser_title();
            if(user.getMedia() !=  null){
                this.file_no = user.getMedia().getFile_no();
                this.file_path = user.getMedia().getFile_path();
                this.file_savedname = user.getMedia().getFile_savedname();
            }
        }
    }
}
