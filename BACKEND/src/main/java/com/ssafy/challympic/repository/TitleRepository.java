package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TitleRepository {

    private final EntityManager em;

    public void saveTitle(Title title) {
        em.persist(title);
    }

    public Title findByChallenge(int challenge_no){
        try {
            return em.createQuery("select t from Title t where t.challenge.challenge_no = :challenge_no", Title.class)
                    .setParameter("challenge_no", challenge_no)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void deleteTitle(Title title){
        em.remove(title);
        em.flush();
    }

    public List<Title> findTitleByUserNo(int user_no) {
        return em.createQuery("select t from Title t where t.user.user_no = :user_no", Title.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

}
