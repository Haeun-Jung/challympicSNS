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
        return (Subscription) em.createQuery("select s from Subscription s where s.challenge_no = :challenge_no and s.user_no = :user_no")
                .setParameter("challenge_no", subscription.getChallenge_no())
                .setParameter("user_no", subscription.getUser_no())
                .getSingleResult();

    }

    public void deleteSubscription(Subscription subscription) {
        em.remove(subscription);
    }

    public List<Challenge> findChallengeByUserNoFromSubs(int userNo) {
        return em.createQuery("select s.challenge_no from Subscription s where s.user_no = :user_no", Challenge.class)
            .setParameter("user_no", userNo)
            .getResultList();
    }
}
