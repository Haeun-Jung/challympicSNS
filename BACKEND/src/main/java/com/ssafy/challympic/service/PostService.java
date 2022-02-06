package com.ssafy.challympic.service;


import com.ssafy.challympic.domain.Challenge;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post getPost(int postNo){
        return postRepository.findByPostNo(postNo);
    }

    @Transactional(readOnly = true)
    public List<Post> getPostList(Integer challengeNo){
        return postRepository.findByChallengNo(challengeNo);
    }

    @Transactional
    public int save(Post post) {
        postRepository.save(post);
        return post.getPost_no();
    }

    @Transactional
    public int update(int postNo, Post newPost){
        Post post = postRepository.findByPostNo(postNo);
        if(newPost.getPost_content() != null){
            post.setPost_content(newPost.getPost_content());
        }

        if(newPost.getMedia() != null){
            post.setMedia(newPost.getMedia());
        }

        return post.getPost_no();
    }

    @Transactional
    public void updateReport(int postNo){
        Post post = postRepository.findByPostNo(postNo);
        post.setPost_report(post.getPost_report()+1);
    }

    @Transactional
    public int delete(int postNo){
        postRepository.deleteByPostNo(postNo);
        return 1;
    }
}
