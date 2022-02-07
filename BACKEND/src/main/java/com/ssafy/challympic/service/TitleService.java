package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Title;
import com.ssafy.challympic.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    @Transactional
    public void saveTitles(Title title) {
        titleRepository.saveTitle(title);
    }

    public List<Title> findTitlesByUserNo(int user_no) {return titleRepository.findTitleByUserNo(user_no);}

}
