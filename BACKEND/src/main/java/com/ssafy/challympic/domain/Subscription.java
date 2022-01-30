package com.ssafy.challympic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Subscription {

    @Id @GeneratedValue
    @Column(name = "subscription_no")
    private int subscription_no;

    private int challenge_no;

    private int user_no;

    public static Subscription setSubscription(int challenge_no, int user_no) {
        Subscription subscription = new Subscription();
        subscription.setChallenge_no(challenge_no);
        subscription.setUser_no(user_no);
        return subscription;
    }

}
