package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SearchApiController {

    private final SearchService searchService;

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
    public Result searchTagList(@PathVariable String tag) {
        List<Challenge> challengeList = searchService.getChallengeListByTagContent(tag);
        List<Post> postList = searchService.getPostListByTagContent(tag);

        Map<String, List> data = new HashMap<>();
        data.put("challengeList", challengeList);
        data.put("postList", postList);

        if(tag.contains("챌린지")) {
            searchService.setSearchChallenge(tag);
        }

        return new Result(true, HttpStatus.OK.value(), data);
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
        return new Result(true, HttpStatus.OK.value(), userList)
    }

}
