package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public List<Comment> findByPost(int post_no){
        return commentRepository.findByPost(post_no);
    }

//    public int findCommentLikeCnt(int )

    public Comment findOne(int comment_no){
        return commentRepository.findOne(comment_no);
    }

    @Transactional
    public void update(int comment_no, String comment_content){
        Comment findComment = commentRepository.findOne(comment_no);
        findComment.setComment_content(comment_content);
        findComment.setComment_update(new Date());
    }

    public void delete(int comment_no){
        Comment comment = commentRepository.findOne(comment_no);
        commentRepository.delete(comment);
    }

    @Transactional
    public void report(int comment_no) {
        Comment comment = commentRepository.findOne(comment_no);
        comment.setComment_report(comment.getComment_report()+1);
    }

    public int commentReportCntByUser(int user_no){
        List<Comment> findCommentList = commentRepository.findByUser(user_no);
        int reportCnt = 0;
        for (Comment comment : findCommentList) {
            reportCnt += comment.getComment_report();
        }
        return reportCnt;
    }
}
