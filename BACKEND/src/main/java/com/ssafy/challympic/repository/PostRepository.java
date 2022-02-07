package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public List<Post> findByChallengNo(int challengeNo){
        return em.createQuery("select p from Post p where p.challenge_no = :challengeNo", Post.class)
                .setParameter("challengeNo", challengeNo)
                .getResultList();
    }

    public Post findByPostNo(int post_no){
        return em.find(Post.class, post_no);
    }

    public void deleteByPostNo(int post_no){
        em.remove(findByPostNo(post_no));
    }


}
