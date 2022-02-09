package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final CommentLikeService commentLikeService;
    private final AlertService alertService;

    @PostMapping("/comment")
    public Result save(@RequestBody CommentRequest request){
        User findUser = userService.findUser(request.user_no);
        Post findPost = postService.getPost(request.post_no);
        if(findUser == null || findPost == null){
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
        Comment comment = new Comment();
        comment.setUser(findUser);
        comment.setPost(findPost);
        comment.setComment_content(request.getComment_content());
        commentService.save(comment);

        // 댓글 작성시 알람 설정
        Alert commentAlert = new Alert();
        User writer = findPost.getUser();
        commentAlert.setUser(writer);
        commentAlert.setAlert_content(findUser.getUser_nickname() + "님이 댓글을 작성했습니다.");
        alertService.saveAlert(commentAlert);

        Alert tagAlert = new Alert();
        String content = request.comment_content;
        String[] splitSharp = content.split(" ");
        for(String str : splitSharp) {
            if(str.startsWith("@")) {
                User tagUser = userService.findByNickname(str.substring(1));
                if(tagUser == null) continue;
                tagAlert.setUser(tagUser);
                tagAlert.setAlert_content(findUser.getUser_nickname() + "님이 댓글에서 태그했습니다.");
                alertService.saveAlert(tagAlert);
            }
        }

        return new Result(true, HttpStatus.OK.value());
    }

    @PutMapping("/comment")
    public Result update(@RequestBody CommentRequest request){
        commentService.update(request.comment_no, request.comment_content);
        return new Result(true, HttpStatus.OK.value());
    }

    @DeleteMapping("/comment")
    public Result delete(@RequestBody CommentRequest request){
        commentService.delete(request.comment_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @PostMapping("/comment/like")
    public Result like(@RequestBody CommentRequest request){
        CommentLike commentLike = commentLikeService.findOne(request.user_no, request.comment_no);
        if(commentLike != null){
            commentLikeService.delete(request.user_no, request.comment_no);
            return new Result(true, HttpStatus.OK.value(), null, false);
        }else{
            User user = userService.findUser(request.user_no);
            Comment comment = commentService.findOne(request.comment_no);
            if(user == null || comment == null){
                return new Result(true, HttpStatus.OK.value(), "유저나 코멘트가 이상", true);
            }
            commentLike = new CommentLike();
            commentLike.setUser(user);
            commentLike.setComment(comment);
            commentLikeService.save(commentLike);
            return new Result(true, HttpStatus.OK.value(), null, true);
        }
    }

    @PutMapping("/report/comment")
    public Result report(@RequestBody CommentRequest request){
        commentService.report(request.comment_no);
        return new Result(true, HttpStatus.OK.value());
    }

    /**
     * 포스트 no로 comment 부르기
     */
    @GetMapping("/comment")
    public Result commentInPost(@RequestBody CommentRequest request){
        List<Comment> commentList = commentService.findByPost(request.post_no);
        List<AdminApiController.CommentDto> collect = commentList.stream()
                .map(c -> {
                    AdminApiController.CommentDto commentDto = new AdminApiController.CommentDto(c);
                    commentDto.setLike_cnt(10);
                    return commentDto;
                }).collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @Data
    static class CommentDto{
        private int comment_no;
        private String comment_content;
        private int like_cnt;
        private Date comment_regdate;
        private int comment_report;

        public CommentDto(Comment comment) {
            this.comment_no = comment.getComment_no();
            this.comment_content = comment.getComment_content();
            this.comment_regdate = comment.getComment_regdate();
            this.comment_report = comment.getComment_report();
        }
    }

    @Data
    static class CommentRequest{
        private int comment_no;
        private int user_no;
        private int post_no;
        private String comment_content;
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;
        private boolean isLiked;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }

        public Result(boolean isSuccess, int code, T data) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.data = data;
        }
    }
}
