package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){ em.persist(user); }

    public User login(String user_email, String user_pwd){
        return em.createQuery("select u from User u where u.user_email = :user_email and u.user_pwd = :user_pwd", User.class)
            .setParameter("user_email", user_email)
            .setParameter("user_pwd", user_pwd)
            .getSingleResult(); }

    public User findOne(int user_no){ return em.find(User.class, user_no); }

    public List<User> findAll(){ return em.createQuery("select u from User u", User.class)
            .getResultList(); }

    public List<User> validateEmail(String user_email){
        return em.createQuery("select u from User u where u.user_email = :user_email", User.class)
                .setParameter("user_email", user_email)
                .getResultList();
    }

    public List<User> validateNickname(String user_nickname){
        return em.createQuery("select u from User u where u.user_nickname = :user_nickname", User.class)
                .setParameter("user_nickname", user_nickname)
                .getResultList();
    }

    public void delete(int user_no){
        User user = em.find(User.class, user_no);
        em.remove(user);
        em.flush();
    }
}