package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_no")
    private int post_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

//    @OneToMany(mappedBy = "post")
//    private List<Comment> comment_no;

    @OneToOne
    @JoinColumn(name = "like_no")
    private PostLike postLike;

    @OneToOne
    @JoinColumn(name = "file_no")
    private Media media;

    private String post_content;

    private int post_report;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP", updatable = false)
    private Date post_regdate;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date post_update;

}
