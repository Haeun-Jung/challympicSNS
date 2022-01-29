package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
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
}
