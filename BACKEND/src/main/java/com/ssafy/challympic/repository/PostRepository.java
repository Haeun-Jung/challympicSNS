package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public List<Post> findByChallengNo(int challengeNo){
        return em.createQuery("select p from Post p where p.challenge_no = :challengeNo", Post.class)
                .setParameter("challengeNo", challengeNo)
                .getResultList();
    }

    public Post findByPostNo(int post_no){
        return em.find(Post.class, post_no);
    }

    public void deleteByPostNo(int post_no){
        em.remove(findByPostNo(post_no));
    }

    public List<Post> findByUser(int user_no){
        return em.createQuery("select p from Post p where p.user.user_no = :user_no", Post.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

    public List<Post> findLikePostByUserNo(int userNo) {
        return em.createQuery("select p from Post p where p.post_no = " +
                "(select pl.post_no from PostLike pl where pl.user_no = :userNo)", Post.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }

    public PostLike findPostLikeByUserNo(int user_no) {
        try{
            return em.createQuery("select pl from PostLike pl where pl.user_no = :user_no", PostLike.class)
                    .setParameter("user_no", user_no)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }
}
