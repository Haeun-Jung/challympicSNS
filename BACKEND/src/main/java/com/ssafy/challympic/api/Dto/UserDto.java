package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Media;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private int user_no;
    private String user_email;
    private String user_nickname;
}
