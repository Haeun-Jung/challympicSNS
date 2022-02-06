package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.domain.defaults.UserAuth;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "email_nickname_unique", columnNames = {"user_email", "user_nickname"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략. AUTO_INCREMENT0
    private int user_no;

    @Column(nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String user_pwd;

    @Column(nullable = false)
    private String user_nickname;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date user_regdate;

    @Column(columnDefinition = "varchar(100) default 'USER'")
    @Enumerated(EnumType.STRING)
    private UserAuth user_auth = UserAuth.USER;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "file_no")
    private Media media;

    private String user_title;

    @Column(columnDefinition = "varchar(100) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserActive user_active = UserActive.ACTIVE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date user_inactivedate;

    @OneToMany(mappedBy = "user")
    private List<Interest> interest;

    @OneToMany(mappedBy = "follow_following_no")
    private List<Follow> following;

    @OneToMany(mappedBy = "follow_follower_no")
    private List<Follow> follower;

    @OneToMany(mappedBy = "user")
    private List<QnA> qna;

    @OneToMany(mappedBy = "user")
    private List<Comment> comment;

    @OneToMany(mappedBy = "user")
    private List<CommentLike> commentLike;
}
