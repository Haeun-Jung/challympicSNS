package com.ssafy.challympic.service;


import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Post> getPostList(int challengeNo){
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

    public int postCntByChallenge(int challenge_no){
        return postRepository.findByChallengNo(challenge_no).size();
    }

    public int postReportCntByUser(int user_no){
        List<Post> findPostList = postRepository.findByUser(user_no);
        int reportCnt = 0;
        for (Post post : findPostList) {
            reportCnt += post.getPost_report();
        }
        return reportCnt;
    }

    public List<Post> getPostListByUserNo(int userNo) {
        return postRepository.findByUser(userNo);
    }

    public List<Post> getLikePostListByUserNo(int userNo) {
        return postRepository.findLikePostByUserNo(userNo);
    }
}
