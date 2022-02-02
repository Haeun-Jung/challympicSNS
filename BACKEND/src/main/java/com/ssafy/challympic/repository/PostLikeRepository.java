package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.PostLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {

    private final EntityManager em;

    @Transactional(readOnly = true)
    public PostLike findPostLikeByUserNoPostNo(int postNo, int userNo){
        return em.createQuery("select ps from PostLike ps where ps.user_no = :userNo and ps.post_no = :postNo", PostLike.class)
                .setParameter("userNo", userNo)
                .setParameter("postNo", postNo)
                .getSingleResult();
    }

    @Transactional
    public void deleteByPostNoUserNo(int postNo, int userNo){
        em.remove(findPostLikeByUserNoPostNo(postNo, userNo));
    }

    @Transactional
    public void save(PostLike postLike){
        em.persist(postLike);
    }

}
