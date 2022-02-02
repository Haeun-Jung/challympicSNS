package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.PostLike;
import com.ssafy.challympic.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLike getPostLikeByUserNoPostNo(int postNo, int userNo){
        return postLikeRepository.findPostLikeByUserNoPostNo(postNo, userNo);
    }

    public void delete(int postNo, int userNo){
        postLikeRepository.deleteByPostNoUserNo(postNo, userNo);
    }

    public Long save(PostLike postLike){
        postLikeRepository.save(postLike);
        PostLike _postLike = getPostLikeByUserNoPostNo(postLike.getPost_no().getPost_no(), postLike.getUser_no().getUser_no());
        return _postLike.getLike_no();
    }
}
