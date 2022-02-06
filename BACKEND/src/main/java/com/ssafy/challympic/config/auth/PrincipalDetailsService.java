package com.ssafy.challympic.config.auth;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.UserAuth;
import com.ssafy.challympic.repository.UserAuthRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:7777/login이 요청될 때 실행된다.

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String user_email) throws UsernameNotFoundException {
//        System.out.println("PrincipalDetailsService의 loadUserByUsername()");
        UserAuth userEntity = userAuthRepository.findByEmail(user_email);
        return new PrincipalDetails(userEntity);
    }
}
