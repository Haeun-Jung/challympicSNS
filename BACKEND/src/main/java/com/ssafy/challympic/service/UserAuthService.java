package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.UserAuth;
import com.ssafy.challympic.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService {

    private final UserAuthRepository userAuthRepository;

    @Transactional
    public void join(UserAuth user){
        userAuthRepository.save(user);
    }

    @Transactional
    public void updatePwd(int user_no, String user_pwd){
        UserAuth user = userAuthRepository.findOne(user_no);
        user.setUser_pwd(user_pwd);
    }

    public UserAuth findUser(int user_no){
        return userAuthRepository.findOne(user_no);
    }
}
