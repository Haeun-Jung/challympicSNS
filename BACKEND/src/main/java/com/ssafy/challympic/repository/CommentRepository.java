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

//    public List<Comment> findByPost(int post_no){
//        return em.createQuery("select c from Comment c where c.post.get")
//    }

    public void update(int comment_no, String comment_content){
        Comment comment = em.find(Comment.class, comment_no);
        comment.setComment_content(comment_content);
    }

    public void delete(int comment_no){
        Comment comment = em.find(Comment.class, comment_no);
        em.remove(comment);
        em.flush();
    }
}
