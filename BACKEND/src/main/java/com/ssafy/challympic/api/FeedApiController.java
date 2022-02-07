package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.ChallengeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FeedApiController {

//    private final PostController postController;
    private final ChallengeService challengeService;
//    private final CommentService commentService;

    /**
     * 내가 만든 챌린지 목록
     * !!!TODO!!!
     * 없는 사람이면 오류 출력해야함.
     * @param userNo
     * @return
     */
    @GetMapping("/feed/{userNo}/challenge")
    public Result getChallengeList(@PathVariable int userNo) {
        List<Challenge> challenges = challengeService.getChallengeByUserNo(userNo);
        List<ChallengeDto> collect = challenges.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value()   , collect);
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
        List<ChallengeDto> collect = challenges.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

}
