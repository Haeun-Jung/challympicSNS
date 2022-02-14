package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.CommentLike;
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

    public List<Post> findRecentPostList(int limit){
        return em.createQuery("select p from Post p order by p.post_regdate desc", Post.class)
                .setMaxResults(limit)
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

    public List<PostLike>  findUserPostLike(int userNo){
        return em.createQuery("select pl from PostLike pl where pl.user_no = :userNo", PostLike.class)
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

    public List<CommentLike> findPostLikeByPostNo(int post_no) {
        return em.createQuery("select cl from CommentLike cl where cl.comment.post.post_no = :post_no", CommentLike.class)
                .setParameter("post_no", post_no)
                .getResultList();
    }
}
