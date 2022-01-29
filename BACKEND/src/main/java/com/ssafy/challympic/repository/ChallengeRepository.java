package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Challenger;
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
        return em.createQuery("select c from Challenge c where c.challenge_title = :title", Challenge.class)
                .setParameter("title", challenge_title)
                .getResultList();
    }

    public void saveChallenger(Challenger challenger) {
        em.persist(challenger);
    }
}
