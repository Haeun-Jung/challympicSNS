package com.ssafy.challympic;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.domain.defaults.ChallengeType;
import com.ssafy.challympic.domain.defaults.UserAuthEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;
//    @PostConstruct
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
            User user = setUser("admin@ssafy.com", "123", "ADMIN");
            em.persist(user);

            User testUser = setUser("test01@ssafy.com", "123", "test");
            em.persist(testUser);

//            Challenge challenge1 = makeChallenge(2, "운동챌린지", "#운동 #건강 열심히", new Date(), "VIDEO", "운동왕");
//            em.persist(challenge1);
        }

        private Challenge makeChallenge(int user_no, String challenge_title, String challenge_content, Date challenge_end, String challenge_type, String title_name) {
            Challenge challenge = new Challenge();
            challenge.setUser(em.find(User.class, user_no));
            challenge.setChallenge_title(challenge_title);
            challenge.setChallenge_content(challenge_content);
            challenge.setChallenge_end(challenge_end);
            if(challenge_type.equals("VIDEO")) challenge.setChallenge_type(ChallengeType.VIDEO);
            else challenge.setChallenge_type(ChallengeType.IMAGE);
            challenge.setChallenge_title(title_name);

            return challenge;
        }

        private User setUser(String user_email, String user_pwd, String user_nickname) {
            User user = new User();
            user.setUser_email(user_email);
            String rawPwd = user_pwd;
            String encPwd = bCryptPasswordEncoder.encode(rawPwd);
            user.setUser_pwd(encPwd);
            // local 환경 동작 시 DB User 테이블 UTF-8 설정이 되어있지 않다면 에러가 발생함 -> 영어로 변경
            user.setUser_nickname(user_nickname);
            if(user_nickname.equals("ADMIN")){
                user.setUser_auth(UserAuthEnum.ADMIN);
            }
            return user;
        }
    }
}
