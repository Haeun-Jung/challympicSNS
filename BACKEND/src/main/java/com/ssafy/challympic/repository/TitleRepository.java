package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Title;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TitleRepository {

    private final EntityManager em;

    public int saveTitle(Title title) {
        em.persist(title);
        em.flush();
        return getTitleByName(title.getTitle_name());
    }

    private int getTitleByName(String title_name){
        List<Title> titles = em.createQuery("select t from Title t where t.title_name = :title_name", Title.class)
                .setParameter("title_name", title_name)
                .getResultList();
        for(Title title : titles) {
            if(title.getUser() == null) {
                return title.getTitle_no();
            }
        }
        return -1;
    }

    // TODO : user no로 타이틀 리스트 가져오기
//    private List<Title>
}
