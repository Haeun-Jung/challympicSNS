package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Challenger;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChallengeRepository {

    private final EntityManager em;

    public List<Challenge> findAll() {
        return em.createQuery("select c from Challenge c", Challenge.class)
                .getResultList();
    }

    public void save(Challenge challenge) {
        em.persist(challenge);
    }

    public List<Challenge> findByTitle(String challenge_title) {
        return em.createQuery("select c from Challenge c where c.challenge_title = :title order by c.challenge_end desc", Challenge.class)
                .setParameter("title", challenge_title)
                .getResultList();
    }

    public void saveChallenger(Challenger challenger) {
        em.persist(challenger);
    }

    public List<Challenge> findByUserNo(int userNo) {
        return em.createQuery("select c from Challenge c where c.user.user_no = :userNo", Challenge.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    public Challenge findByChallengeNo(int challengeNo) {
        return em.createQuery("select c from Challenge c where c.challenge_no = :challenge_no", Challenge.class)
                .setParameter("challenge_no", challengeNo)
                .getSingleResult();
    }

    public Challenge findOne(int challengeNo){
        return em.find(Challenge.class, challengeNo);
    }

    public void saveChallengeTag(ChallengeTag challengeTag) {
        em.persist(challengeTag);
    }
}
