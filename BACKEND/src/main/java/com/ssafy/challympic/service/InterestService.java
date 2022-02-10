package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Interest;
import com.ssafy.challympic.repository.InterestRepository;
import com.ssafy.challympic.repository.TagRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    /**
     * 관심 저장
     */
    public boolean save(int user_no, int tag_no){
        Interest findInterest = interestRepository.findOne(user_no, tag_no);
        if(findInterest != null){
            return false;
        }else{
            Interest interest = new Interest();
            interest.setUser(userRepository.findOne(user_no));
            interest.setTag(tagRepository.findOne(tag_no));
            interestRepository.save(interest);
            return true;
        }
    }

    public List<Interest> findByUser(int user_no){
        return interestRepository.findByUser(user_no);
    }

    public void deleteInterest(int user_no, int tag_no){
        Interest interest = interestRepository.findOne(user_no, tag_no);
        interestRepository.delete(interest);
    }
}
