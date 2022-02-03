package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostLike {

    @Id @GeneratedValue
    private Long like_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private Post post_no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user_no;

    public PostLike(Post post_no, User user_no){
        this.post_no = post_no;
        this.user_no = user_no;
    }

    public PostLike() {}
}
