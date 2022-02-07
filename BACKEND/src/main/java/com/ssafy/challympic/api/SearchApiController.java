package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.PostDto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.SearchService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;
    private final UserService userService;

    @GetMapping("/search")
    public Result getSearchList() {
        List<Tag> tagList = searchService.getTagList();
        List<User> userList = searchService.getUserList();

        Map<String, List> data = new HashMap<>();

        data.put("tagList", tagList);
        data.put("userList", userList);

        return new Result(true, HttpStatus.OK.value(), data);
    }

    @GetMapping("/search/tag/{tag}")
    public Result searchTagList(@PathVariable String tag, @RequestBody UserRequest request) {
        List<Challenge> challenges = searchService.getChallengeListByTagContent("#" + tag);
        List<Post> posts = searchService.getPostListByTagContent("#" + tag);

        List<ChallengeDto> challengeList = challenges.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        List<PostDto> postList = posts.stream()
                .map(p -> new PostDto(p))
                .collect(Collectors.toList());

        Map<String, List> data = new HashMap<>();
        data.put("challengeList", challengeList);
        data.put("postList", postList);

        // 검색 기록 저장
        User user = userService.findUser(request.user_no);
        searchService.setSearchRecord("#" + tag, user);

        return new Result(true, HttpStatus.OK.value(), data);
    }

    @Data
    static class UserRequest {
        private int user_no;
    }

    @GetMapping("/search/recent/user/{userNo}")
    public Result searchRecentListByUser(@PathVariable int userNo){
        List<Search> searchList = searchService.getTagListByUserNo(userNo);
        return new Result(true, HttpStatus.OK.value(), searchList);
    }

    @GetMapping("/search/trending")
    public Result searchTrending() {
        List<Challenge> challengeList = searchService.getTrendChallenge();
        return new Result(true, HttpStatus.OK.value(), challengeList);
    }

    @GetMapping("/search/rank")
    public Result getRank() {
        List<User> userList = searchService.getRank();
        return new Result(true, HttpStatus.OK.value(), userList);
    }

}
