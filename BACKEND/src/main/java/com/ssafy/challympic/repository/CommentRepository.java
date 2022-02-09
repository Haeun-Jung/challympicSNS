package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment){
        em.persist(comment);
    }

    public List<Comment> findByPost(int post_no){
        return em.createQuery("select c from Comment c where c.post.post_no = :post_no", Comment.class)
                .setParameter("post_no", post_no)
                .getResultList();
    }

    public Comment findOne(int comment_no){
        return em.find(Comment.class, comment_no);
    }

    public void delete(Comment comment){
        em.remove(comment);
        em.flush();
    }

    public List<Comment> findByUser(int user_no){
        return em.createQuery("select c from Comment c where c.user.user_no = :user_no", Comment.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }
}
