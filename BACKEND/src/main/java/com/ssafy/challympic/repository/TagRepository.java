package com.ssafy.challympic.repository;

import com.ssafy.challympic.domain.ChallengeTag;
import com.ssafy.challympic.domain.PostTag;
import com.ssafy.challympic.domain.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepository {

    private final EntityManager em;

    public void saveTag(Tag tag){ em.persist(tag); }

    public Tag findOne(int tag_no){
        return em.find(Tag.class, tag_no);
    }

    public Tag findTagByTagContent(String tagContent) {
        try{
            return em.createQuery("select t from Tag t where t.tag_content = :tagContent", Tag.class)
                    .setParameter("tagContent", tagContent)
                    .getSingleResult();
        }catch (NoResultException e) {
            return null;
        }
    }

    public Tag validateTagContent(String tagContent) {
        try{
            return em.createQuery("select t from Tag t where t.tag_content = :tagContent", Tag.class)
                    .setParameter("tagContent", tagContent)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<ChallengeTag> findChallengeTagByChallengeNo(int challenge_no) {
        return em.createQuery("select ct from ChallengeTag ct where ct.challenge.challenge_no = :challenge_no", ChallengeTag.class)
                .setParameter("challenge_no", challenge_no)
                .getResultList();
    }

    public void deleteChallengeTag(ChallengeTag ct) {
        em.remove(ct);
//        em.flush();
    }

    public void savePostTag(PostTag postTag) {
        em.persist(postTag);
    }

    public List<Tag> findPostTagListByPostNo(int postNo) {
        return em.createQuery("select t from Tag t where t.tag_no = " +
                "(select pt.tag.tag_no from PostTag pt where pt.post.post_no = :postNo)", Tag.class)
                .setParameter("postNo", postNo)
                .getResultList();
    }

    public List<PostTag> findPostTagList(int post_no) {
        return em.createQuery("select pt from PostTag pt where pt.post.post_no = :post_no", PostTag.class)
                .setParameter("post_no", post_no)
                .getResultList();
    }

    public List<Tag> findAllTagList() {
        return em.createQuery("select t from Tag t", Tag.class)
                .getResultList();
    }
}
