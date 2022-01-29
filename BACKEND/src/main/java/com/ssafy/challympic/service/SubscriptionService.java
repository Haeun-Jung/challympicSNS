package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Subscription;
import com.ssafy.challympic.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
