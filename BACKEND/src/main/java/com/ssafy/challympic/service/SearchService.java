package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.ChallengeRepository;
import com.ssafy.challympic.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final ChallengeRepository challengeRepository;

    public List<Tag> getTagList() {
        return searchRepository.findTagList();
    }

    public List<User> getUserList(){
        return searchRepository.findUserList();
    }

    public List<Search> getTagListByUserNo(int userNo) {
        return searchRepository.findTagListByUserNo(userNo);
    }

    public List<Challenge> getChallengeListByTagContent(String tag) {
        return searchRepository.findChallengeByTagContent(tag);
    }

    public List<Post> getPostListByTagContent(String tag) {
        return searchRepository.findPostByTagContent(tag);
    }

    @Transactional
    public void setSearchChallenge(String tag) {
        Challenge challenge = challengeRepository.findByTitle(tag).get(0);
        SearchChallenge searchChallenge = new SearchChallenge();
        searchChallenge.setChallenge(challenge);
        searchRepository.setSearchChallenge(searchChallenge);
    }

    public List<Challenge> getTrendChallenge() {
        return searchRepository.findChallengeByTrend();
    }

    public List<User> getRank() {
        return searchRepository.findRank();
    }
}
