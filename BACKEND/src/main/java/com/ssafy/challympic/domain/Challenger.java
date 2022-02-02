package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Challenger {

    @Id @GeneratedValue
    @Column(name = "challenger_no")
    private int challenger_no;

    @JoinColumn(name = "user_no")
    private int user_no;

    @JoinColumn(name = "challenge_no")
    private int challenge_no;
}
