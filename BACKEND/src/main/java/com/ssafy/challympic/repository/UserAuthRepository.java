package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.UserAuth;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@AllArgsConstructor
public class UserAuthRepository {

    private final EntityManager em;

    public UserAuth findOne(int user_no){ return em.find(UserAuth.class, user_no); }

    public UserAuth findByEmail(String user_email){
        return em.createQuery("select u from UserAuth u where u.user_email = :user_email", UserAuth.class)
                .setParameter("user_email", user_email)
                .getSingleResult();
    }

    public void save(UserAuth user) {
        em.persist(user);
    }
}
