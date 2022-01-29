package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Challenge {

    @Id @GeneratedValue
    @Column(name = "challenge_no")
    private int challenge_no;

//    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private int user_no;

//    @OneToMany(fetch = LAZY)
    @JoinColumn(name = "challenger_no")
    private int challenger_no;

    private LocalDateTime challenge_start;

    private LocalDateTime challenge_end;

    private String challenge_access;

    private String challenge_type;

    private String challenge_title;

    private String challenge_content;

    private boolean challenge_official;

    private int challenge_report;

//    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "title_no")
    private int title_no;
}
