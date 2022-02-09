package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void save(Tag tag){ em.persist(tag); }

    public Tag findOne(int tag_no){
        return em.find(Tag.class, tag_no);
    }

    public List<Tag> findByTagContent(String tagContent) {
        return em.createQuery("select t from Tag t where t.tag_content = :tagContent", Tag.class)
                .setParameter("tagContent", tagContent)
                .getResultList();
    }

    public List<Tag> validateTagContent(String tagContent) {
        return em.createQuery("select t from Tag t where t.tag_content = :tagContent", Tag.class)
                .setParameter("tagContent", tagContent)
                .getResultList();
    }

    public List<ChallengeTag> findByChallengeNo(int challenge_no) {
        return em.createQuery("select ct from ChallengeTag ct where ct.challenge.challenge_no = :challenge_no", ChallengeTag.class)
                .setParameter("challenge_no", challenge_no)
                .getResultList();
    }

    public void deleteTags(ChallengeTag ct) {
        em.remove(ct);
        em.flush();
    }
}
