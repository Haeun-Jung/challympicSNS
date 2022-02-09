package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubscriptionRepository {

    private final EntityManager em;

    public void saveSubscription(Subscription subscription) {
        em.persist(subscription);
    }

    public Subscription findSubscription(Subscription subscription) {
        try{
            return (Subscription) em.createQuery("select s from Subscription s where s.challenge = :challenge_no and s.user = :user_no")
                    .setParameter("challenge_no", subscription.getChallenge())
                    .setParameter("user_no", subscription.getUser())
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }

    }

    public void deleteSubscription(Subscription subscription) {
        em.remove(subscription);
    }

    public List<Challenge> findChallengeByUserNoFromSubs(int userNo) {
        return em.createQuery("select s.challenge from Subscription s where s.user.user_no = :user_no", Challenge.class)
            .setParameter("user_no", userNo)
            .getResultList();
    }

    public List<Subscription> findSubscriptionByChallenge(int challenge_no){
        return em.createQuery("select s from Subscription s where s.challenge.challenge_no = :challenge_no", Subscription.class)
                .setParameter("challenge_no", challenge_no)
                .getResultList();
    }

    public Subscription findSubscriptionByChallengeAndUser(int challengeNo, int userNo) {
        try{
            return em.createQuery("select s from Subscription s where s.challenge.challenge_no = :challengeNo and s.user.user_no = :userNo", Subscription.class)
                    .setParameter("challengeNo", challengeNo)
                    .setParameter("userNo", userNo)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }
}
