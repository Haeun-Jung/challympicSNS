package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;

    public void save(Follow follow){ em.persist(follow); }

    public Follow findOne(int follow_following_no, int follow_follower_no){
        try{
            Follow follow = em.createQuery("select f from Follow f where f.follow_following_no.user_no = :follow_following_no and f.follow_follower_no.user_no = :follow_follower_no", Follow.class)
                    .setParameter("follow_following_no", follow_following_no)
                    .setParameter("follow_follower_no", follow_follower_no)
                    .getSingleResult();
            return follow;
        }catch (NoResultException e){
            return null;
        }
    }

    /**
     * 내가 팔로우 한 사람
     * @param user_no 내 번호
     * @return
     */
    public List<Follow> findUserFollowing(int user_no){
        return em.createQuery("select f from Follow f where f.follow_following_no.user_no = :user_no", Follow.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

    /**
     * 나를 팔로우 한 사람
     * @param user_no 내 번호
     * @return
     */
    public List<Follow> findUserFollower(int user_no){
        return em.createQuery("select f from Follow f where f.follow_follower_no.user_no = :user_no", Follow.class)
                .setParameter("user_no", user_no)
                .getResultList();
    }

    public void delete(Follow follow) {
        em.remove(follow);
        em.flush();
    }
}
