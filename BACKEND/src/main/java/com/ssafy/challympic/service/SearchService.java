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

    private final int maxChallenge = 1000000; //TODO : 현재 챌린지 지우면서 찾기 ㅇ어려워짐

    public List<Challenge> findTrendChallenge() {
        List<Challenge> searchedChallenges = searchRepository.findChallengeByTrend();
        List<Challenge> allChallenge = challengeRepository.findAll();
        int challengeSize = allChallenge.size();
        int[][] challengeCount = new int[maxChallenge][2];
        for(int i = 0; i < maxChallenge; i++) challengeCount[i][0] = i + 1;
        for(Challenge c : searchedChallenges) {
            challengeCount[c.getChallenge_no() - 1][1]++;
        }
        Arrays.sort(challengeCount, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        List<Challenge> trendChallenge = new ArrayList<>();
        for(int i = 0; i <= (Math.min(challengeSize, 4)); i++) {
            int challengeNo = challengeCount[i][0];
            if(challengeCount[i][1] != 0) trendChallenge.add(challengeRepository.findByChallengeNo(challengeNo));
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
