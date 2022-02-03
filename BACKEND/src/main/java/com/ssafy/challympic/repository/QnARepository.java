package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.QnA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class QnARepository {

    private final EntityManager em;

    public void save(QnA qna){ em.persist(qna); }

    public List<QnA> findByUser(int user_no){
        return em.createQuery("select q from QnA q where q.user.user_no = :user_no", QnA.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }
}
