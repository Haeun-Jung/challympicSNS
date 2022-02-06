package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Title;
import com.ssafy.challympic.repository.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TitleService {

    private final TitleRepository titleRepository;

    @Transactional
    public int saveTitles(Title title) {
        return titleRepository.saveTitle(title);
    }

}
