package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Title {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "title_no")
    private int title_no;

    private String title_name;

    private int user_no;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;
}
