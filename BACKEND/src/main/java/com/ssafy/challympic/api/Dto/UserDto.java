package com.ssafy.challympic.api.Dto;

import com.ssafy.challympic.domain.Media;
import com.ssafy.challympic.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private int user_no;
    private String user_email;
    private String user_nickname;

    public UserDto(User user) {
        this.user_no = user.getUser_no();
        this.user_email = user.getUser_email();
        this.user_nickname = user.getUser_nickname();
    }
}
