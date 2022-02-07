package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.ChallengeRepository;
import com.ssafy.challympic.repository.SearchRepository;
import com.ssafy.challympic.repository.TagRepository;
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
    private final TagRepository tagRepository;

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

    public List<Challenge> getTrendChallenge() {
        return searchRepository.findChallengeByTrend();
    }

    public List<User> getRank() {
        return searchRepository.findRank();
    }

    @Transactional
    public void setSearchRecord(String search_content, User user) {
        Tag tag = tagRepository.findByTagContent(search_content);
        Search search = new Search();
        search.setSearch_content(search_content);
        search.setUser(user);
        if(tag != null) search.setTag(tag);
        searchRepository.saveSearchRecord(search);
    }
}
