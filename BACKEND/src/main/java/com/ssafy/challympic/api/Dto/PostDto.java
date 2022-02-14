package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class PostDto{
    private int post_no;
    private int user_no;
    private String user_nickname;
    private String user_title;
    private int challenge_no;
    private String chalenge_title;
    private int file_no;
    private String file_path;
    private String file_savedname;
    private String post_content;
    private int post_report;
    private Date post_regdate;
    private Date post_update;
    private int post_like_count;
    private int comment_count;
    private boolean IsLike = false;

    public PostDto(Post post, String challengeTitle, int postLikeCount, int postCommentCount, boolean isLike) {
        this.post_no = post.getPost_no();
        this.user_no = post.getUser().getUser_no();
        this.user_nickname = post.getUser().getUser_nickname();
        this.user_title = post.getUser().getUser_title();
        this.challenge_no = post.getChallenge_no();
        this.chalenge_title = challengeTitle;
        this.file_no = post.getMedia().getFile_no();
        this.file_path = post.getMedia().getFile_path();
        this.file_savedname = post.getMedia().getFile_savedname();
        this.post_content = post.getPost_content();
        this.post_report = post.getPost_report();
        this.post_regdate = post.getPost_regdate();
        this.post_update = post.getPost_update();
        this.post_like_count = postLikeCount;
        this.comment_count = postCommentCount;
        this.IsLike = isLike;
    }
}