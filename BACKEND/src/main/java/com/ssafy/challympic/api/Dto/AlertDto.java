package com.ssafy.challympic.api.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AlertDto {
    private int user_no;
    private String alert_content;
    private boolean alert_confirm;
    private Date alert_regDate;
}
