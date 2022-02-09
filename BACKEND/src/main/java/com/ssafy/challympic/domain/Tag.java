package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tag_no;

    private String tag_content;

    private String isChallenge;

    @OneToMany(mappedBy = "tag", fetch = LAZY)
    private List<Interest> interest;

}
