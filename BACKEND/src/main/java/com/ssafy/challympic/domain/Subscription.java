package com.ssafy.challympic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_no")
    private int subscription_no;

    @ManyToOne
    @JoinColumn(name = "challenge_no")
    private Challenge challenge;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    public static Subscription setSubscription(Challenge challenge, User user) {
        Subscription subscription = new Subscription();
        subscription.setChallenge(challenge);
        subscription.setUser(user);
        return subscription;
    }

}
