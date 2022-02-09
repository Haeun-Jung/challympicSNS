package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Challenger;
import com.ssafy.challympic.domain.defaults.ChallengeAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    public void deleteChallenger(Challenger challenger){
        em.remove(challenger);
        em.flush();
    }

    public List<Challenger> findChallengerList(int challenge_no){
        return em.createQuery("select cr from Challenger cr where cr.challenge.challenge_no = :challenge_no", Challenger.class)
                .setParameter("challenge_no", challenge_no)
                .getResultList();
    }


    public List<Challenge> findByUserNo(int userNo) {
        return em.createQuery("select c from Challenge c where c.user.user_no = :userNo", Challenge.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    public Challenge findByChallengeNo(int challengeNo) {
        try{
            return em.createQuery("select c from Challenge c where c.challenge_no = :challenge_no", Challenge.class)
                    .setParameter("challenge_no", challengeNo)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }

    }

    public Challenge findOne(int challengeNo){
        try{
            return em.createQuery("select c from Challenge c where c.challenge_no = :challenge_no", Challenge.class)
                    .setParameter("challenge_no", challengeNo)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    public void saveChallengeTag(ChallengeTag challengeTag) {
        em.persist(challengeTag);
    }

    public List<Challenger> findChallengerByChallengeNo(int challenge_no) {
        return em.createQuery("select c from Challenger c where c.challenge.challenge_no = :challenge_no", Challenger.class)
                .setParameter("challenge_no", challenge_no)
                .getResultList();
    }

    public void deleteChallenger(Challenger c) {
        em.remove(c);
    }
}
