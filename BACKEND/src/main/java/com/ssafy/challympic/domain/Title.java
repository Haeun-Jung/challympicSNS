package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Title {

    @Id @GeneratedValue
    @Column(name = "title_no")
    private int title_no;

    private String title_name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;


    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;
}
