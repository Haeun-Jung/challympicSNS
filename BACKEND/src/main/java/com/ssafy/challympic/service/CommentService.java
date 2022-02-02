package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

//    public List<Comment> findByPost(int post_no){
//        return commentRepository.findByPost(post_no);
//    }

    public void update(int comment_no, String comment_content){
        commentRepository.update(comment_no, comment_content);
    }

    public void delete(int comment_no){
        commentRepository.delete(comment_no);
    }
}
