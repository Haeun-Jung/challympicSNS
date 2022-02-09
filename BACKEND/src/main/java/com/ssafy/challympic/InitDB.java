package com.ssafy.challympic;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.UserAuthEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;
    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{

        private final EntityManager em;
        private final BCryptPasswordEncoder bCryptPasswordEncoder;
        public void dbInit(){
            User user = new User();
            user.setUser_email("admin@ssafy.com");
            String rawPwd = "123";
            String encPwd = bCryptPasswordEncoder.encode(rawPwd);
            user.setUser_pwd(encPwd);
            // local 환경 동작 시 DB User 테이블 UTF-8 설정이 되어있지 않다면 에러가 발생함 -> 영어로 변경
            user.setUser_nickname("Admin");
            user.setUser_auth(UserAuthEnum.ADMIN);

            em.persist(user);

            User testUser = new User();
            testUser.setUser_email("test01@ssafy.com");
            String testPwd = "123";
            String encTestPwd = bCryptPasswordEncoder.encode(testPwd);
            testUser.setUser_pwd(encTestPwd);
            testUser.setUser_nickname("test");

            em.persist(testUser);
        }
    }
}
