package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.domain.defaults.UserActive;
import com.ssafy.challympic.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final TitleRepository titleRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final QnARepository qnARepository;

    public List<User> userList(){
        return adminRepository.findAllUser();
    }

    @Transactional
    public void updateUserActive(int user_no){
        User findUser = userRepository.findOne(user_no);
        findUser.setUser_active(UserActive.INACTIVE);
    }

    @Transactional
    public void deleteUser(int user_no){
        User findUser = userRepository.findOne(user_no);
        userRepository.delete(findUser);
    }

    public List<Challenge> challengeList(){
        return adminRepository.findAllChallenge();
    }

    @Transactional
    public void deleteChallenge(int challenge_no){
        Title findTitle = titleRepository.findByChallenge(challenge_no);
        Challenge findChallenge = challengeRepository.findOne(challenge_no);
        titleRepository.deleteTitle(findTitle);
        adminRepository.deleteChallenge(findChallenge);
    }

    public List<Post> postList(){
        return adminRepository.findAllPost();
    }

    @Transactional
    public void deletePost(Post post){
        postRepository.deleteByPostNo(post.getPost_no());
    }

    public List<Comment> commentList(){
        return adminRepository.findAllComment();
    }

    @Transactional
    public void deleteComment(int comment_no){
        Comment findComment = commentRepository.findOne(comment_no);
        commentRepository.delete(findComment);
    }

    @Transactional
    public void updateQnA(QnA qna, String qna_content){
        qna.setQna_answer(qna_content);
//        시간 update
    }
}
