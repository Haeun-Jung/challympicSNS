package com.ssafy.challympic.domain;

import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.domain.defaults.UserAuthEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class UserAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성 전략. AUTO_INCREMENT0
    @Column(name = "user_no")
    private int user_no;

    @Column(nullable = false)
    private String user_email;

    @Column(nullable = false)
    private String user_pwd;

    @Column(columnDefinition = "varchar(100) default 'USER'")
    @Enumerated(EnumType.STRING)
    private UserAuthEnum user_auth = UserAuthEnum.USER;

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date user_regdate;

    @Column(columnDefinition = "varchar(100) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserActive user_active = UserActive.ACTIVE;

}
