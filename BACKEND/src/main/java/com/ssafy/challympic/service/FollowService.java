package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Follow;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.FollowRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean follow(int following_no, int follower_no){
        Follow follow = followRepository.findOne(following_no, follower_no);
        if(follow == null ){
            follow = new Follow();
            User following = userRepository.findOne(following_no);
            User follower = userRepository.findOne(follower_no);
            follow.setFollow_following_no(following);
            follow.setFollow_follower_no(follower);
            followRepository.save(follow);
            return true;
        }else{
            followRepository.delete(follow);
            return false;
        }
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


    public boolean isFollow(int following_no, int follower_no) {
        Follow follow = new Follow();
        follow = followRepository.findOne(following_no, follower_no);
        if(follow != null){
            return true;
        }else {
            return false;
        }
    }

    public int followingCnt(int user_no) {
        List<Follow> userFollower = followRepository.findUserFollower(user_no);
        if(!userFollower.isEmpty()){
            return userFollower.size();
        }else{
            return 0;
        }
    }

    public int followerCnt(int user_no) {
        List<Follow> userFollowing = followRepository.findUserFollowing(user_no);
        if(!userFollowing.isEmpty()){
            return userFollowing.size();
        }else{
            return 0;
        }
    }
}

