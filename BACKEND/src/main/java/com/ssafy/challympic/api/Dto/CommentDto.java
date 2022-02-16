package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {
    private int comment_no;
    private int user_no;
    private int post_no;
    private String comment_content;
    private Date comment_regdate;
    private Date comment_update;
    private int like_cnt;
    private int comment_report;
    private boolean IsLiked;
    private String user_nickname;
    private int file_no;
    private String user_profile;

    public CommentDto(Comment comment, boolean IsLiked) {
        this.comment_no = comment.getComment_no();
        this.user_no = comment.getUser().getUser_no();
        this.post_no = comment.getPost().getPost_no();
        this.comment_content = comment.getComment_content();
        this.comment_regdate = comment.getComment_regdate();
        this.comment_update = comment.getComment_update();
        this.like_cnt = comment.getCommentLike().size();
        this.comment_report = comment.getComment_report();
        this.IsLiked = IsLiked;
        this.user_nickname = comment.getUser().getUser_nickname();
        if(comment.getUser().getMedia() != null){
            this.user_profile = comment.getUser().getMedia().getFile_path()+"/"+comment.getUser().getMedia().getFile_savedname();
        }
    }
}
