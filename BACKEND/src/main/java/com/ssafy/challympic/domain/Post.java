package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_no")
    private int post_no;

    @JoinColumn(name = "user_no")
    private int user_no;

    @JoinColumn(name = "challenge_no")
    private int challenge_no;

    @JoinColumn(name = "comment_no")
    private int comment_no;

    @JoinColumn(name = "like_no")
    private int like_no;

    @JoinColumn(name = "file_no")
    private int file_no;

    private String post_content;

    private int post_report;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", updatable = false)
    private Date post_regdate;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date post_update;

}
