package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@Table(uniqueConstraints = {@UniqueConstraint(name = "email_nickname_unique", columnNames = {"user_email", "user_nickname"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략. AUTO_INCREMENT
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
    private UserAuth user_auth;

    @OneToOne
    @JoinColumn(name = "file_no")
    private File file;

    private String user_title;

    @Column(columnDefinition = "varchar(100) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserActive user_active;

    @Temporal(TemporalType.TIMESTAMP)
    private Date user_inactivedate;

    @OneToMany(mappedBy = "user")
    private List<Interest> interest;

}
