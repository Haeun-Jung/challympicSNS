package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;


    public List<Challenge> findChallenges() {
        return challengeRepository.findAll();
    }
}
