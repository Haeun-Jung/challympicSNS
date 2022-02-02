package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InterestRepository {

    private final EntityManager em;

    public void save(Interest interest){
        em.persist(interest);
    }

    public List<Interest> findByUser(int user_no){
        return em.createQuery("select i from Interest i where i.user.user_no = :user_no", Interest.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

    public Interest findOne(int user_no, int tag_no){
        return em.createQuery("select i from Interest i where i.user.user_no = :user_no and i.tag.tag_no = :tag_no", Interest.class)
                .setParameter("user_no", user_no)
                .setParameter("tag_no", tag_no)
                .getSingleResult();
    }

    public void delete(Interest interest){
        em.remove(interest);
        em.flush();
    }

}
