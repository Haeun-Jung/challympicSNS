package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.ChallengeService;
import com.ssafy.challympic.service.SubscriptionService;
import com.ssafy.challympic.service.TitleService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChallengeApiController {

    private final ChallengeService challengeService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final TitleService titleService;

    /**
     * 챌린지 목록
     */
    @GetMapping("/challenge")
    public Result challenges() {
        List<Challenge> findChallenges = challengeService.findChallenges();
        List<ChallengeDto> collect = findChallenges.stream()
                .map(c -> new ChallengeDto(c.getChallenge_no(), c.getUser(), c.getChallenge_start(), c.getChallenge_end(), c.getChallenge_access(), c.getChallenge_type(), c.getChallenge_title(), c.getChallenge_content(), c.isChallenge_official(), c.getChallenge_report(), c.getTitle_no()))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }


    /**
     * 챌린지 등록
     */
    @PostMapping("/challenge")
    public Result createChallenge(@RequestBody CreateChallengeRequest request) {

        ChallengeAccess challenge_access; // 권한
        List<Integer> challengers = new ArrayList<>(); // 초대된 인원
        // 챌린지 초대한 사람이 없으면 PUBLIC
        if(request.getChallengers().size() == 0){
            challenge_access = ChallengeAccess.PUBLIC;
        }
        // 챌린지 초대한 사람이 있으면 PRIVATE
        else{
            challenge_access = ChallengeAccess.PRIVATE;
            for(String user_nickname : request.getChallengers()) {
                User challengerUser = userService.findByNickname(user_nickname);
                challengers.add(challengerUser.getUser_no());
            }
        }

        User user = userService.findUser(request.user_no);

        Title title = new Title();
        title.setTitle_name(request.getTitle_name());
        int title_no = titleService.saveTitles(title);
        if(title_no == -1) return new Result(false, HttpStatus.FORBIDDEN.value());

        Challenge challenge = Challenge.createChallenge(
                user,
                request.getChallenge_end(),
                challenge_access,
                request.getChallenge_type(),
                request.getChallenge_title(),
                request.getChallenge_content(),
                title_no
        );
        int challengeNo = challengeService.saveChallenge(challenge);

        for(int cr : challengers) {
            Challenger challenger = new Challenger();
            challenger.setChallenge_no(challengeNo);
            challenger.setUser_no(cr);
            challengeService.saveChallengers(challenger);
        }

        title.setChallenge(challenge);

        return new Result(true, HttpStatus.OK.value());
    }

    @Data
    static class CreateChallengeRequest {
        private int user_no;
        private List<String> challengers;
        private Date challenge_end;
        private ChallengeType challenge_type;
        private String challenge_title;
        private String challenge_content;
        private String title_name;
    }

    /**
     * 한글 pathVariable 인코딩 관련 이슈
     * @param challengeTitle
     * @return
     */
    @GetMapping("/challenge/confirm/{challengeTitle}")
    public Result ChallengeTitleCheck(@PathVariable String challengeTitle) {
        List<Challenge> challenges = challengeService.findChallengeByTitle(challengeTitle);
        for(Challenge c : challenges) {
            if(c.getChallenge_end().after(new Date())){
                return new Result(false, HttpStatus.FORBIDDEN.value());
            }
        }
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독추가
     * //====subscription이 존재하는지 확인!!====//
     * @param challengeNo
     * @param userNo
     * @return
     */
    @PostMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result addSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        subscriptionService.saveSubscription(Subscription.setSubscription(challengeNo, userNo));
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 구독 취소
     */
    @DeleteMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result removeSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        subscriptionService.deleteSubscription(Subscription.setSubscription(challengeNo, userNo));
        return new Result(true, HttpStatus.OK.value());
    }

    @Data
    @AllArgsConstructor
    static class ChallengeDto {
        private int challenge_no;
        private User user;
        private Date challenge_start;
        private Date challenge_end;
        private ChallengeAccess challenge_access;
        private ChallengeType challenge_type;
        private String challenge_title;
        private String challenge_content;
        private boolean challenge_official;
        private int challenge_report;
        private int title_no;
    }
}
