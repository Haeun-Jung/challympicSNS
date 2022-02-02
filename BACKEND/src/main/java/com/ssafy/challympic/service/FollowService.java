package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Follow;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.FollowRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(int following_no, int follower_no){
        User following = userRepository.findOne(following_no);
        User follower = userRepository.findOne(follower_no);
        System.out.println("service");
        System.out.println(following.getUser_no()+" "+follower.getUser_no());
        Follow follow = new Follow();
        follow.setFollow_following_no(following);
        follow.setFollow_follower_no(follower);
        followRepository.save(follow);
    }

    /**
     * 내가 팔로우 한 사람들
     * @param user_no
     * @return
     */
    public List<User> following(int user_no){
        List<Follow> following = followRepository.findUserFollowing(user_no);
        List<User> followingList = following.stream()
                .map(f ->
                        userRepository.findOne(f.getFollow_follower_no().getUser_no()))
                .collect(Collectors.toList());
        return followingList;
    }

    /**
     * 나를 팔로우 한 사람들
     * @param user_no
     * @return
     */
    public List<User> follower(int user_no){
        List<Follow> follower = followRepository.findUserFollower(user_no);
        List<User> followerList = follower.stream()
                .map(f ->
                    userRepository.findOne(f.getFollow_following_no().getUser_no()))
                .collect(Collectors.toList());
        return followerList;
    }


}

