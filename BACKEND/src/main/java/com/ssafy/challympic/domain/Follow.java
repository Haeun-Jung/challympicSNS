package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int follow_no;

    /**
     * 내가 follow한 사람
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_following_no")
    private User follow_following_no;

    /**
     * 나를 follow한 사람
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_follower_no")
    private User follow_follower_no;
}
