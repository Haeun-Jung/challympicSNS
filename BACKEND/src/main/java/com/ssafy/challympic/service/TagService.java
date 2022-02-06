package com.ssafy.challympic.service;

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
        Tag tag = new Tag();
        tag.setTag_content(tag_content);
        tagRepository.save(tag);
    }

    /**
     * 태그 자동 검색
     */
    public List<Tag> autocomplete(String tag_content){
        return tagRepository.autocomplete(tag_content);
    }

    /**
     * 태그 선택
     */
    public Tag findOne(int tag_no){
        return tagRepository.findOne(tag_no);
    }
}
