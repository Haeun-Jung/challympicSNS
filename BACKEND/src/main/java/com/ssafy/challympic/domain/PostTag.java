package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostTag {

    @Id @GeneratedValue
    @Column(name = "post_tag_no")
    private int post_tag_no;

    @ManyToOne
    @JoinColumn(name = "post_no")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "tag_no")
    private Tag tag;
}
