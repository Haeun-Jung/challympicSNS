package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.repository.ChallengeRepository;
import com.ssafy.challympic.repository.SearchRepository;
import com.ssafy.challympic.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final TagRepository tagRepository;
    private final ChallengeRepository challengeRepository;

    public List<Tag> findTagList() {
        List<Tag> tagList = searchRepository.findTagList();
        tagList.removeIf(t -> t.getIsChallenge() != null);
        return tagList;
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
        List<Challenge> searchedChallenges = searchRepository.findChallengeByTrend();
        List<Challenge> allChallenge = challengeRepository.findAll();
        int challengeSize = allChallenge.size();
        List<int[]> challengeCount = new ArrayList<>();

        for(Challenge c : searchedChallenges) {
            boolean isFind = false;
            for(int[] count : challengeCount) {
                if(count[0] == c.getChallenge_no()) {
                    count[1]++;
                    isFind = true;
                }
            }
            if(!isFind) {
                challengeCount.add(new int[]{c.getChallenge_no(), 0});
            }
        }

        challengeCount.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        List<Challenge> trendChallenge = new ArrayList<>();
        System.out.println(challengeSize);
        for(int i = 0; i <= (Math.min(challengeSize-1, 4)); i++) {
            int challengeNo = challengeCount.get(i)[0];
            trendChallenge.add(challengeRepository.findByChallengeNo(challengeNo));
        }

        return trendChallenge;
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
