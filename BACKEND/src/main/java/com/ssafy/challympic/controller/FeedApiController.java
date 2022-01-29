package com.ssafy.challympic.controller;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.service.ChallengeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

//    private final PostController postController;
    private final ChallengeService challengeService;
//    private final CommentService commentService;

    /**
     * 내가 만든 챌린지 목록
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/challenge")
    public FeedChallengeResponse getChallengeList(@PathVariable int userNo) {
        List<Challenge> challenges = challengeService.getChallengeByUserNo(userNo);
        return new FeedChallengeResponse(true, 200, challenges);
    }

    @GetMapping("/feed/{userNo}/subscription")
    public FeedChallengeResponse getSubscriptionChallengeList(@PathVariable int userNo) {
        List<Challenge> challenges = challengeService.getChallengeBySubscription(userNo);
        return new FeedChallengeResponse(true, 200, challenges);
    }


    @Data
    @AllArgsConstructor
    static class FeedChallengeResponse<T> {
        boolean isSuccess;
        int code;
        T data;
    }
}
