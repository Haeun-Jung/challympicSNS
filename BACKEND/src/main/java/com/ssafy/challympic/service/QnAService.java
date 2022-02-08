package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.QnA;
import com.ssafy.challympic.repository.QnARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QnAService {

    private final QnARepository qnARepository;

    public QnA findOne(int qna_no){ return qnARepository.findOne(qna_no); }

    @Transactional
    public void save(QnA qnA){
        qnARepository.save(qnA);
    }

    public List<QnA> findByUser(int user_no){
        return qnARepository.findByUser(user_no);
    }
}
