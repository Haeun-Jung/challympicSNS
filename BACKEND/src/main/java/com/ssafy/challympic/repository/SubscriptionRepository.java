package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository {

    private final EntityManager em;

    public void saveSubscription(Subscription subscription) {
        em.persist(subscription);
    }
}
