package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubscriptionDto {
    private int subscription_no;
    private String message;
}
