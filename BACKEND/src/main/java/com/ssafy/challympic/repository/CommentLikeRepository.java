package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepository {

    private final EntityManager em;

    public void save(CommentLike commentLike){ em.persist(commentLike); }

    public CommentLike findOne(int user_no, int comment_no){
        return em.createQuery("select cl from CommentLike cl where cl.user.user_no = :user_no and cl.comment.comment_no = :comment_no", CommentLike.class)
                .setParameter("user_no", user_no)
                .setParameter("comment_no", comment_no)
                .getSingleResult();
    }

    public void delete(CommentLike commentLike){
        em.remove(commentLike);
        em.flush();
    }
}
