package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Subscription;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionDto {
    private int subscription_no;
    private int challenge_no;
    private String challenge_title;

    public SubscriptionDto(Subscription subscription) {
        this.subscription_no = subscription.getSubscription_no();
        this.challenge_no = subscription.getChallenge().getChallenge_no();
        this.challenge_title = subscription.getChallenge().getChallenge_title();
    }
}
