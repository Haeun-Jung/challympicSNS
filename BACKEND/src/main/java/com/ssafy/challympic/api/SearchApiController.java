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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        List<PostDto> postList = posts.stream()
                .map(p -> new PostDto(
                        p.getPost_no(),
                        p.getUser().getUser_no(),
                        p.getUser().getUser_nickname(),
                        p.getUser().getUser_title(),
                        p.getChallenge_no(),
                        challengeService.findChallengeByChallengeNo(p.getChallenge_no()).getChallenge_title(),
                        p.getMedia().getFile_no(),
                        p.getMedia().getFile_path(),
                        p.getMedia().getFile_savedname(),
                        p.getPost_content(),
                        p.getPost_report(),
                        p.getPost_regdate(),
                        p.getPost_update(),
                        postService.getPostLikeCountByPostNo(p.getPost_no()),
                        commentService.postCommentCnt(p.getPost_no())
                ))
                .collect(Collectors.toList());

        Map<String, List> data = new HashMap<>();
        data.put("challengeList", challengeList);
        data.put("postList", postList);

        if(request.user_no != 0){
            // 검색 기록 저장
            User user = userService.findUser(request.user_no);
            if(user != null) searchService.saveSearchRecord("#" + request.tag_content, user);

            Tag findTag = tagService.findTagByTagContent("#" + request.tag_content);

            if(findTag.getIsChallenge() != null && findTag.getIsChallenge().equals("challenge")) {
                SearchChallenge searchChallenge = new SearchChallenge();
                searchChallenge.setChallenge(challengeService.findChallengeByTitle("#" + request.tag_content).get(0));
                searchChallenge.setUser(user);
                searchService.saveSearchChallenge(searchChallenge);
            }
        }

        return new Result(true, HttpStatus.OK.value(), data);
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
        List<Challenge> challenges = searchService.findTrendChallenge();
        List<ChallengeDto> challengeList = challenges.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), challengeList);
    }

    @GetMapping("/search/rank")
    public Result getRank() {
        List<User> userList = searchService.findRank();
        return new Result(true, HttpStatus.OK.value(), userList);
    }

}
