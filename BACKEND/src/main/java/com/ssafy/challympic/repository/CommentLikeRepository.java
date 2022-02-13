package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepository {

    private final EntityManager em;

    public void save(CommentLike commentLike){ em.persist(commentLike); }

    public CommentLike findOne(int user_no, int comment_no){
        try{
            CommentLike commentLike = em.createQuery("select cl from CommentLike cl where cl.user.user_no = :user_no and cl.comment.comment_no = :comment_no", CommentLike.class)
                    .setParameter("user_no", user_no)
                    .setParameter("comment_no", comment_no)
                    .getSingleResult();
            return commentLike;
        }catch (NoResultException e){
            return null;
        }
    }

    public List<CommentLike> findLikeCntByComment(int comment_no){
        return em.createQuery("select cl from CommentLike cl where cl.comment.comment_no = :comment_no", CommentLike.class)
                .setParameter("comment_no", comment_no)
                .getResultList();
    }

    public void delete(CommentLike commentLike){
        em.remove(commentLike);
        em.flush();
    }

    public boolean findIsLikeByUser(int user_no, int comment_no) {
        try{
            CommentLike result = em.createQuery("select cl from CommentLike cl where cl.comment.comment_no = :comment_no and cl.user.user_no = :user_no", CommentLike.class)
                    .setParameter("comment_no", comment_no)
                    .setParameter("user_no", user_no)
                    .getSingleResult();
            return true;
        }catch (NoResultException e){
            return false;
        }
    }
}
