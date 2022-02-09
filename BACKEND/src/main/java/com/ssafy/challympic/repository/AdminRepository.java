package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public List<User> findAllUser(){
        return em.createQuery("select u from User u", User.class)
            .getResultList();
    }

    public void deleteUser(User user){
        em.remove(user);
        em.flush();
    }

    public List<Challenge> findAllChallenge(){
        return em.createQuery("select c from Challenge c", Challenge.class)
                .getResultList();
    }

    public void deleteChallenge(Challenge challenge){
        em.remove(challenge);
        em.flush();
    }

    public List<Post> findAllPost(){
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public void deletePost(Post post){
        em.remove(post);
        em.flush();
    }

    public List<Comment> findAllComment(){
        return em.createQuery("select c from Comment c", Comment.class)
                .getResultList();
    }

    public void deleteComment(Comment comment){
        em.remove(comment);
        em.flush();
    }

    public List<QnA> findAllQnA(){
        return em.createQuery("select q from QnA q", QnA.class)
                .getResultList();
    }

}
