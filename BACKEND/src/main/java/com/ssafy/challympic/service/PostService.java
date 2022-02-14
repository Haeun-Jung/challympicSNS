package com.ssafy.challympic.service;


import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.CommentLike;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.PostLike;
import com.ssafy.challympic.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<Post> getRecentPostList(int limit){
        return postRepository.findRecentPostList(limit);
    }

    @Transactional
    public int save(Post post) {
        postRepository.save(post);
        return post.getPost_no();
    }

    @Transactional
    public int update(Integer postNo, Post newPost){
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
        List<PostLike> userPostLike = postRepository.findUserPostLike(userNo);
        List<Post> posts = new ArrayList<>();
        if(!userPostLike.isEmpty()){
            posts = userPostLike.stream()
                    .map(pl -> {
                        Post post = postRepository.findByPostNo(pl.getPost_no());
                        return post;
                    }).collect(Collectors.toList());
        }
        return posts;
    }

    public boolean getPostLikeByUserNo(int user_no) {
        PostLike pl = postRepository.findPostLikeByUserNo(user_no);
        if(pl == null) return false;
        else return true;
    }

    public int getPostLikeCountByPostNo(int post_no) {
        List<CommentLike> commentLikeList = postRepository.findPostLikeByPostNo(post_no);
        return commentLikeList.size();
    }
}
