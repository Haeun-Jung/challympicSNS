package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityRepository {

    private final EntityManager em;

    @Transactional
    public void saveActivity(Activity activity) {
        em.persist(activity);
    }

    public List<Activity> findActivityListByUserNo(int userNo) {
        return em.createQuery("select a from Activity a where a.user_no = :userNo", Activity.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }
}
