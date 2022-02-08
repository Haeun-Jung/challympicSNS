package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AlertRepository {

    private final EntityManager em;

    public void saveAlert(Alert alert) {
        em.persist(alert);
    }

    public List<Alert> findAlertByUserNo(int userNo) {
        return em.createQuery("select a from Alert a where a.user.user_no = :userNo", Alert.class)
                .setParameter("userNo", userNo)
                .getResultList();
    }
}
