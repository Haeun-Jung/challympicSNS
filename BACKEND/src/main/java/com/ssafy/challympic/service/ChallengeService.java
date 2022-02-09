package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Challenger;
import com.ssafy.challympic.repository.ChallengeRepository;
import com.ssafy.challympic.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final SubscriptionRepository subscriptionRepository;

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
            if(c.getChallenge_end().after(new Date())){
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

    public List<Challenge> findChallengeBySubscription(int userNo) {
        return subscriptionRepository.findChallengeByUserNoFromSubs(userNo);
    }

    public Challenge findChallengeByChallengeNo(int challengeNo) {
        return challengeRepository.findByChallengeNo(challengeNo);
    }

    @Transactional
    public void saveChallengeTag(ChallengeTag challengeTag) {
        challengeRepository.saveChallengeTag(challengeTag);
    }

    public int challengeReportCntByUser(int user_no){
        List<Challenge> findChallengeList = challengeRepository.findByUserNo(user_no);
        int reportCnt = 0;
        for (Challenge challenge : findChallengeList) {
            reportCnt += challenge.getChallenge_report();
        }
        return reportCnt;
    }
}
