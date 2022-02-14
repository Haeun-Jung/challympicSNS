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
    private final TagRepository tagRepository;

    public List<Tag> findTagList() {
        return searchRepository.findTagList();
    }

    public List<User> findUserList(){
        return searchRepository.findUserList();
    }

    public List<Search> findTagListByUserNo(int userNo) {
        return searchRepository.findTagListByUserNo(userNo);
    }

    public List<Challenge> findChallengeListByTagContent(String tag) {
        return searchRepository.findChallengeByTagContent(tag);
    }

    public List<Post> findPostListByTagContent(String tag) {
        return searchRepository.findPostByTagContent(tag);
    }

    public List<Challenge> findTrendChallenge() {
        return searchRepository.findChallengeByTrend();
    }

    public List<User> findRank() {
        return searchRepository.findRank();
    }

    @Transactional
    public void saveSearchRecord(String search_content, User user) {
        Tag tag = tagRepository.findTagByTagContent(search_content);
        Search search = new Search();
        search.setSearch_content(search_content);
        search.setUser(user);
        if(tag != null) {
            search.setTag_no(tag.getTag_no());
            search.setTag_content(tag.getTag_content());
        }
        searchRepository.saveSearchRecord(search);
    }

    @Transactional
    public void saveSearchChallenge(SearchChallenge searchChallenge){
        searchRepository.saveSearchChallenge(searchChallenge);
    }
}
