package com.ssafy.challympic.controller;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.service.ChallengeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChallengeApiController {

    private final ChallengeService challengeService;

    @GetMapping("/challenge")
    public ChallengesResult challenges() {
        List<Challenge> findChallenges = challengeService.findChallenges();
        List<ChallengeDto> collect = findChallenges.stream()
                .map(c -> new ChallengeDto(c.getChallenge_no(), c.getUser_no(), c.getChallenger_no(), c.getChallenge_start(), c.getChallenge_end(), c.getChallenge_access(), c.getChallenge_type(), c.getChallenge_title(), c.getChallenge_content(), c.isChallenge_official(), c.getChallenge_report(), c.getTitle_no()))
                .collect(Collectors.toList());
        return new ChallengesResult(collect);
    }

    @Data
    @AllArgsConstructor
    static class ChallengesResult<T> {
        private T data;
    }

    @PostMapping("/challenge")
    public CreateChallengeResponse createChallenge(@RequestBody CreateChallengeRequest request) {

    }

    static class CreateChallengeResponse {

    }

    static class CreateChallengeRequest {
        private int user_nickname;
        private List<String> challengers;
        private LocalDateTime challenge_start;
        private LocalDateTime challenge_end;
        private String challenge_access;
        private String challenge_type;
        private String challenge_content;
        private int title_no;
    }

    @Data
    @AllArgsConstructor
    static class ChallengeDto {
        private int challenge_no;
        private int user_no;
        private int challenger_no;
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
