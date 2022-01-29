package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Challenger;
import com.ssafy.challympic.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    public List<Challenge> getChallengeByUserNo(int userNo) {
        return challengeRepository.findByUserNo(userNo);
    }


    public List<Challenge> findChallenges() {
        return challengeRepository.findAll();
    }

    @Transactional
    public int saveChallenge(Challenge challenge) {
        // 중복 확인
        validateDuplicateChallenge(challenge);
        challengeRepository.save(challenge);
        return challenge.getChallenge_no();
    }

    private void validateDuplicateChallenge(Challenge challenge) {
        List<Challenge> findChallenges = challengeRepository.findByTitle(challenge.getChallenge_title());
        for(Challenge c : findChallenges) {
            if(c.getChallenge_end().isAfter(LocalDateTime.now())){
                throw new IllegalStateException("이미 존재하는 챌린지입니다.");
            }
        }
    }

    @Transactional
    public void saveChallengers(Challenger challenger) {
        challengeRepository.saveChallenger(challenger);
    }

    public List<Challenge> findChallengeByTitle(String title) {
        return challengeRepository.findByTitle(title);
    }
}
