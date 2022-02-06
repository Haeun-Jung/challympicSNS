package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Search {

    @Id
    @GeneratedValue
    @Column(name = "search_no")
    private int search_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;

    private String search_content;

    @Column(columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date search_regdate;
}
