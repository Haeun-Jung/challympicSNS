package com.ssafy.challympic.api.Dto;

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

}
