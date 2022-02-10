package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final EntityManager em;

    public void saveActivity(Activity activity) {
        em.persist(activity);
    }

    public List<Activity> findActivityListByUserNo(int userNo) {
        return em.createQuery("select a from Activity a where a.user_no = :userNo", Activity.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }
}
