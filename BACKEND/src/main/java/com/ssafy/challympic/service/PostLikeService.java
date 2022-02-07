package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.PostLike;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public List<PostLike> getPostLikeListByPostNo(Integer postNo){
        return postLikeRepository.findByPostNo(postNo);
    }

    public List<PostLike> getPostLikeByUserNoPostNo(Integer postNo, Integer userNo){
        return postLikeRepository.findPostLikeByUserNoPostNo(postNo, userNo);
    }

    public void delete(Integer postNo, Integer userNo){
        postLikeRepository.deleteByPostUser(postNo, userNo);
    }

    public void save(PostLike postLike){
        postLikeRepository.save(postLike);
    }
}
