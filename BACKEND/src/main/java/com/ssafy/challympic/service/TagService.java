package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.PostTag;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    /**
     * 태그 저장
     */
    @Transactional
    public void saveTag(String tag_content){
        Tag isTag = tagRepository.validateTagContent(tag_content);
        if(isTag != null) return;
        Tag tag = new Tag();
        tag.setTag_content(tag_content);
        tagRepository.saveTag(tag);
    }

    /**
     * 챌린지 제목 태그로 저장
     */
    @Transactional
    public void saveTag(String challenge_title, boolean isTitle){
        Tag isTag = tagRepository.validateTagContent(challenge_title);
        if(isTag != null) return;
        Tag tag = new Tag();
        tag.setTag_content(challenge_title);
        if(isTitle) tag.setIsChallenge("challenge");
        tagRepository.saveTag(tag);
    }

    /**
     * 태그 선택
     */
    public Tag findOne(int tag_no){
        return tagRepository.findOne(tag_no);
    }

    public Tag findTagByTagContent(String tagContent) {
        List<Tag> tagList = tagRepository.findByTagContent(tagContent);
        if(tagList.isEmpty()) return null;
        return tagList.get(0);
    }

    public void savePostTag(PostTag postTag) {
        tagRepository.savePostTag(postTag);
    }

    public List<Tag> findPostTagListByPostNo(int postNo) {
        return tagRepository.findPostTagListByPostNo(postNo);
    }

    public List<PostTag> findPostTagList(int post_no) {
        return tagRepository.findPostTagList(post_no);
    }

    public List<Tag> findAllTagList() {
        return tagRepository.findAllTagList();
    }
}
