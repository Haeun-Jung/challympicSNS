package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.service.ChallengeService;
import com.ssafy.challympic.service.PostService;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ChallengeDto {
    private int challenge_no;
    private int user_no;
    private Date challenge_start;
    private Date challenge_end;
    private ChallengeAccess challenge_access;
    private ChallengeType challenge_type;
    private String challenge_title;
    private String challenge_content;
    private boolean challenge_official;
    private int challenge_report;
    private List<PostDto> postList;

    public ChallengeDto(Challenge challenge) {
        this.challenge_no = challenge.getChallenge_no();
        this.user_no = challenge.getUser().getUser_no();
        this.challenge_start = challenge.getChallenge_start();
        this.challenge_end = challenge.getChallenge_end();
        this.challenge_access = challenge.getChallenge_access();
        this.challenge_type = challenge.getChallenge_type();
        this.challenge_title = challenge.getChallenge_title();
        this.challenge_content = challenge.getChallenge_content();
        this.challenge_official = challenge.isChallenge_official();
        this.challenge_report = challenge.getChallenge_report();
    }

    public ChallengeDto(Challenge challenge, List<PostDto> postList) {
        this.challenge_no = challenge.getChallenge_no();
        this.user_no = challenge.getUser().getUser_no();
        this.challenge_start = challenge.getChallenge_start();
        this.challenge_end = challenge.getChallenge_end();
        this.challenge_access = challenge.getChallenge_access();
        this.challenge_type = challenge.getChallenge_type();
        this.challenge_title = challenge.getChallenge_title();
        this.challenge_content = challenge.getChallenge_content();
        this.challenge_official = challenge.isChallenge_official();
        this.challenge_report = challenge.getChallenge_report();
        this.postList = postList;
    }
}