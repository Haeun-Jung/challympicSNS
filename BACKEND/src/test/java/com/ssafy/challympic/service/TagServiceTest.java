package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(value = false)
public class TagServiceTest {

    @Autowired TagService tagService;

    @Test
    public void 태그저장() throws Exception{
        //given
        tagService.save("코딩");
        //when

        //then

     }
}