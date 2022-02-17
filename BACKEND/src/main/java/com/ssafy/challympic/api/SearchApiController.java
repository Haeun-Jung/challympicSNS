package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.api.Dto.SearchDto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;
    private final UserService userService;
    private final TagService tagService;
    private final ChallengeService challengeService;
    private final PostService postService;
    private final CommentService commentService;
    private final SubscriptionService subscriptionService;
    private final ActivityService activityService;
    private final PostLikeService postLikeService;

    @GetMapping("/search")
    public Result getSearchList() {
        List<Tag> tagList = searchService.findTagList();
        List<User> users = searchService.findUserList();
        List<UserDto> userList = users.stream()
                .map(u -> new UserDto(u.getUser_no(), u.getUser_nickname()))
                .collect(Collectors.toList());

        Map<String, List> data = new HashMap<>();

        data.put("tagList", tagList);
        data.put("userList", userList);

        return new Result(true, HttpStatus.OK.value(), data);
    }

    @Data
    @AllArgsConstructor
    static class UserDto{
        private int user_no;
        private String user_nickname;
    }

    /**
     * 태그 검색
     */
    @PostMapping("/search/tag")
    public Result searchTagList(@RequestBody TagSearchRequest request) {
        List<Challenge> challenges = searchService.findChallengeListByTagContent("#" + request.tag_content);
        List<Post> posts = searchService.findPostListByTagContent("#" + request.tag_content);
        List<ChallengeDto> challengeList = challenges.stream()
                .map(c -> {
                    List<Post> postListByChallengeNo = postService.getPostList(c.getChallenge_no());
                    List<PostDto> postList = postToDto(postListByChallengeNo, request.user_no);
                    boolean isSubscription = subscriptionService.findSubscriptionByChallengeAndUser(c.getChallenge_no(), c.getUser().getUser_no()) != null;
                    return new ChallengeDto(c, postList, isSubscription);
                })
                .collect(Collectors.toList());
        List<PostDto> postList = postToDto(posts, request.getUser_no());

        Map<String, List> data = new HashMap<>();
        data.put("challengeList", challengeList);
        data.put("postList", postList);

        if(request.user_no > 0){
            // 검색 기록 저장
            User user = userService.findUser(request.user_no);
            if(user != null) {
                searchService.saveSearchRecord("#" + request.tag_content, user);
            }

            List<Challenge> tagContainChallenges = challengeService.findChallengesByTag("#" + request.tag_content);
            for(Challenge c : tagContainChallenges) {
                SearchChallenge sc = new SearchChallenge();
                sc.setUser(user);
                sc.setChallenge(c);
                searchService.saveSearchChallenge(sc);
            }
        }

        List<Post> tagContainPost = postService.getPostByTag(request.tag_content);
        for(Post p : tagContainPost) {
            Activity activity = new Activity();
            activity.setPost_no(p.getPost_no());
            activity.setUser_no(request.getUser_no());
            activityService.saveActivity(activity);
        }

        return new Result(true, HttpStatus.OK.value(), data);
    }

    private List<PostDto> postToDto(List<Post> posts, Integer userNo) {
        return posts.stream()
                .map(p -> {
                    String challengeTitle = challengeService.findChallengeByChallengeNo(p.getChallenge_no()).getChallenge_title();
                    List<PostLike> postLikeList = postLikeService.getPostLikeListByPostNo(p.getPost_no());
                    int commentCount = commentService.postCommentCnt(p.getPost_no());
                    boolean isLike = postService.getPostLikeByPostNoAndUserNo(p.getPost_no(), userNo);
                    return new PostDto(p,challengeTitle, postLikeList.size(), commentCount, isLike);
                })
                .collect(Collectors.toList());
    }

    @Data
    static class TagSearchRequest {
        private int user_no;
        private String tag_content;
    }

    @GetMapping("/search/recent/user/{userNo}")
    public Result searchRecentListByUser(@PathVariable int userNo){
        List<Search> searchs = searchService.findTagListByUserNo(userNo);
        List<SearchDto> searchList = searchs.stream()
                .map(s -> new SearchDto(s.getSearch_no(), s.getUser().getUser_no(), s.getTag_no(), s.getTag_content(), s.getSearch_content(), s.getSearch_regdate()))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), searchList);
    }

    @GetMapping("/search/trending")
    public Result searchTrending() {
        List<Challenge> trendChallenge = searchService.findTrendChallenge();
        List<ChallengeDto> trendChallengeList = trendChallenge.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), trendChallengeList);
    }

    @GetMapping("/search/rank")
    public Result getRank() {
        List<User> users = searchService.findRank();
        List<UserDto> userList = users.stream()
                .map(u -> new UserDto(u.getUser_no(), u.getUser_nickname()))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), userList);
    }

}
