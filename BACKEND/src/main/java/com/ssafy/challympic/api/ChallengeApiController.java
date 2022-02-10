package com.ssafy.challympic.api;

import com.ssafy.challympic.api.Dto.ChallengeDto;
import com.ssafy.challympic.api.Dto.SubscriptionDto;
import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    private final TagService tagService;

    /**
     * 챌린지 목록
     */
    @GetMapping("/challenge")
    public Result challenges() {
        List<Challenge> findChallenges = challengeService.findChallenges();
        for(Challenge c : findChallenges) {
            System.out.println(c);
        }
        List<ChallengeDto> collect = findChallenges.stream()
                .map(c -> new ChallengeDto(c))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    private final AlertService alertService;

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
            for(String str : request.getChallengers()) {
                String user_nickname = str.substring(1);
                User challengerUser = userService.findByNickname(user_nickname);
                if(challengerUser == null) return new Result(false, HttpStatus.BAD_REQUEST.value());
                challengers.add(challengerUser.getUser_no());
            }
        }

        // Title 저장
        Title title = new Title();
        title.setTitle_name(request.getTitle_name());
        if(title == null) return new Result(false, HttpStatus.FORBIDDEN.value());

        User user = userService.findUser(request.user_no);

        Challenge challenge = Challenge.createChallenge(
                user,
                request.getChallenge_end(),
                challenge_access,
                request.getChallenge_type(),
                request.getChallenge_title(),
                request.getChallenge_content()
        );

        // 챌린지 엔티티 등록
        challengeService.saveChallenge(challenge);


        // 챌린저 저장
        for(int cr : challengers) {
            Challenger challenger = new Challenger();
            challenger.setChallenge(challenge);
            User _challenger = userService.findUser(cr);
            challenger.setUser(_challenger);
            challengeService.saveChallengers(challenger);

            // 태그된사람 알림
            Alert alert = new Alert();
            alert.setUser(_challenger);
            alert.setAlert_content(user.getUser_nickname() + "님이 챌린지에 초대했습니다.");
            alertService.saveAlert(alert);

        }


        // 내용 파싱해서 태그 저장
        String content = request.challenge_content;
        List<String> tagContentList = new ArrayList<>();
        StringBuilder sb = null;

        // 챌린지 제목 저장
        tagContentList.add(request.challenge_title);
        tagService.saveTag(request.challenge_title, true);
        for(char c : content.toCharArray()) {
            if(c == '#') {
                if(sb != null) {
                    tagContentList.add(sb.toString());
                    tagService.saveTag(sb.toString());
                }
                sb = new StringBuilder();
            }
            if(c == ' ' && sb != null) {
                tagService.saveTag(sb.toString());
                tagContentList.add(sb.toString());
                sb = null;
            }
            if(sb == null) continue;
            sb.append(c);
        }

        if(sb != null) {
            tagService.saveTag(sb.toString());
            tagContentList.add(sb.toString());
        }

        // 챌린지 태그 저장
        for(String s : tagContentList) {
            ChallengeTag challengeTag = new ChallengeTag();
            challengeTag.setTag(tagService.findTagByTagContent(s));
            challengeTag.setChallenge(challenge);
            challengeService.saveChallengeTag(challengeTag);
        }

        // title 등록
        title.setChallenge(challenge);
        titleService.saveTitles(title);

        List<Challenge> challengeList = challengeService.findChallengeByTitle(request.challenge_title);
        int _challenge_no = 0;
        for(Challenge chall :challengeList) {
            if(chall.getChallenge_end().after(new Date())) {
                _challenge_no = chall.getChallenge_no();
            }
        }

        Integer challenge_no = _challenge_no;

        return new Result(true, HttpStatus.OK.value(), challenge_no);
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
     * 해결 방법 : https://velog.io/@aszxvcb/Spring-PathVariable-%ED%95%9C%EA%B8%80-%ED%8C%8C%EB%9D%BC%EB%AF%B8%ED%84%B0-%EC%A0%84%EB%8B%AC
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
     * @param challengeNo
     * @param userNo
     * @return
     */
    @PostMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result addSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        User user = userService.findUser(userNo);

        Subscription findSubscription = subscriptionService.findSubscriptionByChallengeAndUser(challengeNo, userNo);
        if(findSubscription == null){
            subscriptionService.saveSubscription(Subscription.setSubscription(challenge, user));
            Subscription sub = subscriptionService.findSubscriptionByChallengeAndUser(challengeNo, userNo);
            return new Result(true, HttpStatus.OK.value(), new SubscriptionDto(sub.getSubscription_no(), "구독 추가"));
        }else{
            subscriptionService.deleteSubscription(findSubscription);
            return new Result(true, HttpStatus.OK.value(), new SubscriptionDto(findSubscription.getSubscription_no(), "구독 취소"));
        }
    }

    @GetMapping("/challenge/{challengeNo}")
    public Result getChallenge(@PathVariable int challengeNo) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) return new Result(false, HttpStatus.BAD_REQUEST.value());
        else return new Result(true, HttpStatus.OK.value(), challenge);
    }


    /**
     * !!!! 폐기 !!!!
     * 구독 취소
     */
    @DeleteMapping("/challenge/{challengeNo}/subscribe/{userNo}")
    public Result removeSubscription(@PathVariable int challengeNo, @PathVariable int userNo) {
        Challenge challenge = challengeService.findChallengeByChallengeNo(challengeNo);
        if(challenge == null) {
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        User user = userService.findUser(userNo);

        subscriptionService.deleteSubscription(Subscription.setSubscription(challenge, user));
        return new Result(true, HttpStatus.OK.value());
    }
}
