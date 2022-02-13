package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.CommentLike;
import com.ssafy.challympic.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void save(CommentLike commentLike){
        commentLikeRepository.save(commentLike);
    }

    public CommentLike findOne(int user_no, int comment_no){
        return commentLikeRepository.findOne(user_no, comment_no);
    }

    public void delete(int user_no, int comment_no){
        CommentLike commentLike = commentLikeRepository.findOne(user_no, comment_no);
        commentLikeRepository.delete(commentLike);
    }

    public int findLikeCntByComment(int comment_no){
        int cnt = commentLikeRepository.findLikeCntByComment(comment_no).size();
        return cnt;
    }

    public boolean findIsLikeByUser(int user_no,int comment_no) {
        return commentLikeRepository.findIsLikeByUser(user_no, comment_no);
    }
}


