package com.ssafy.challympic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int like_no;

    private Integer post_no;

    private Integer user_no;

    public PostLike(int like_no, Integer post_no, Integer user_no){
        this.like_no = like_no;
        this.post_no = post_no;
        this.user_no = user_no;
    }

    public PostLike(Integer post_no, Integer user_no){
        this.post_no = post_no;
        this.user_no = user_no;
    }

    public PostLike() {}
}
