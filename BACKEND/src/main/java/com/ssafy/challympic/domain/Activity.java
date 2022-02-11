package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Activity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_no")
    private int activity_no;

    private int user_no;

    private int post_no;
}
