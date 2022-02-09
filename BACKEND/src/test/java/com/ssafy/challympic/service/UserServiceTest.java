package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception{
        //given
        User user = new User();
//        UserAuth userAuth = new UserAuth();
//        user.setUser_nickname("member01");
//        user.setUser_email("member01@ssafy.com");
//        userAuth.setUser_email("member01@ssafy.com");
//        userAuth.setUser_pwd("123");

        //when
        int joinId = userService.join(user);

        //then
        assertEquals(user, userRepository.findOne(joinId));
     }

    @Test
    @Rollback(value = false)
    public void 이메일_중복() throws Exception{
        //given
        String email = "fullsun@ssafy.com";

        //when

        //then
        System.out.println(userService.validateDuplicateEmail(email));

     }

    @Test
    @Rollback(value = false)
    public void 닉네임_중복() throws Exception{
        //given
        String nickname = "fullsun606";

        //when

        //then
        System.out.println(userService.validateDuplicateNickname(nickname));

    }

}