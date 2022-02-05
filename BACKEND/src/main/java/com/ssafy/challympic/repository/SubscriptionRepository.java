package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository {

    private final EntityManager em;

    public void saveSubscription(Subscription subscription) {
        em.persist(subscription);
    }

    public Subscription findSubscription(Subscription subscription) {
        return (Subscription) em.createQuery("select s from Subscription s where s.challenge = :challenge_no and s.user = :user_no")
                .setParameter("challenge_no", subscription.getChallenge())
                .setParameter("user_no", subscription.getUser())
                .getSingleResult();

    }

    public void deleteSubscription(Subscription subscription) {
        em.remove(subscription);
    }

    public List<Challenge> findChallengeByUserNoFromSubs(int userNo) {
        return em.createQuery("select c from Challenge c where c.challenge_no = (select s.challenge.challenge_no from Subscription s where s.user = :user_no)", Challenge.class)
            .setParameter("user_no", userNo)
            .getResultList();
    }
}
