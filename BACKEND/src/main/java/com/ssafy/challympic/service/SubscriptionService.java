package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional
    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.saveSubscription(subscription);
    }

    @Transactional
    public void deleteSubscription(Subscription subscription) {
        Subscription sub = subscriptionRepository.findSubscription(subscription);
        subscriptionRepository.deleteSubscription(sub);
    }

    public int findSubscriptionByChallenge(int challenge_no){
        return subscriptionRepository.findSubscriptionByChallenge(challenge_no).size();
    }

    public Subscription findSubscriptionByChallengeAndUser(int challengeNo, int userNo) {
        return subscriptionRepository.findSubscriptionByChallengeAndUser(challengeNo, userNo);
    }

    public List<Subscription> findSubscriptionByUser(int userNo) {
        return subscriptionRepository.findSubscriptionByUser(userNo);
    }
}
