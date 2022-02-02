package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.service.ChallengeService;
import com.ssafy.challympic.service.SubscriptionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChallengeApiController {

    private final ChallengeService challengeService;
//    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/challenge")
    public ChallengesResult challenges() {
        List<Challenge> findChallenges = challengeService.findChallenges();
        List<ChallengeDto> collect = findChallenges.stream()
                .map(c -> new ChallengeDto(c.getChallenge_no(), c.getUser_no(), c.getChallenge_start(), c.getChallenge_end(), c.getChallenge_access(), c.getChallenge_type(), c.getChallenge_title(), c.getChallenge_content(), c.isChallenge_official(), c.getChallenge_report(), c.getTitle_no()))
                .collect(Collectors.toList());
        return new ChallengesResult(true, 200, collect);
    }

    @Data
    @AllArgsConstructor
    static class ChallengesResult<T> {
        boolean isSuccess;
        int code;
        private T data;
    }

//    @PostMapping("/challenge")
//    public ChallengeResponse createChallenge(@RequestBody CreateChallengeRequest request) {
//
//        String challenge_access; // 권한
//        List<Integer> challengers = new ArrayList<>(); // 초대된 인원
//        // 챌린지 초대한 사람이 없으면 PUBLIC
//        if(request.getChallengers().size() == 0){
//            challenge_access = "PUBLIC";
//        }
//        // 챌린지 초대한 사람이 있으면 PRIVATE
//        else{
//            challenge_access = "PRIVATE";
//            for(String user_nickname : request.getChallengers()) {
//                int challengerUserNo = userService.findUser(user_nickname);
//                challengers.add(challengerUserNo);
//            }
//        }
//
//        Challenge challenge = Challenge.createChallenge(
//                request.getUser_no(),
//                request.getChallenge_start(),
//                request.getChallenge_end(),
//                challenge_access,
//                request.getChallenge_type(),
//                request.getChallenge_title(),
//                request.getChallenge_content()
//
//        );
//        int challengeNo = challengeService.saveChallenge(challenge);
//
//        for(int cr : challengers) {
//            Challenger challenger = new Challenger();
//            challenger.setChallenge_no(challengeNo);
//            challenger.setUser_no(cr);
//            challengeService.saveChallengers(challenger);
//        }
//        return new ChallengeResponse(true, 200);
//    }

    @Data
    @AllArgsConstructor
    static class ChallengeResponse {
        boolean isSuccess;
        int code;
    }

    @Data
    static class CreateChallengeRequest {
        private int user_no;
        private List<String> challengers;
        private LocalDateTime challenge_start;
        private LocalDateTime challenge_end;
//        private String challenge_access; // 잠시 대기
        private String challenge_type;
        private String challenge_title;
        private String challenge_content;
        private int title_no; //TODO
    }

    /**
     * 한글 pathVariable 인코딩 관련 이슈
     * @param challengeTitle
     * @return
     */
    @GetMapping("/challenge/confirm/{challengeTitle}")
    public ChallengeResponse ChallengeTitleCheck(@PathVariable String challengeTitle) {
        List<Challenge> challenges = challengeService.findChallengeByTitle(challengeTitle);
        for(Challenge c : challenges) {
            if(c.getChallenge_end().isAfter(LocalDateTime.now())){
                return new ChallengeResponse(false, 400);
            }
        }
        return new ChallengeResponse(true, 500);
    }

    /**
     * 구독추가
     * //====subscription이 존재하는지 확인!!====//
     * @param challengeNo
     * @param userNo
     * @return
     */
    @PostMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public ChallengeResponse addSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        subscriptionService.saveSubscription(Subscription.setSubscription(challengeNo, userNo));
        return new ChallengeResponse(true, 200);
    }

    @DeleteMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public ChallengeResponse removeSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        subscriptionService.deleteSubscription(Subscription.setSubscription(challengeNo, userNo));
        return new ChallengeResponse(true, 200);
    }

    @Data
    @AllArgsConstructor
    static class ChallengeDto {
        private int challenge_no;
        private int user_no;
        private LocalDateTime challenge_start;
        private LocalDateTime challenge_end;
        private String challenge_access;
        private String challenge_type;
        private String challenge_title;
        private String challenge_content;
        private boolean challenge_official;
        private int challenge_report;
        private int title_no;
    }
}
