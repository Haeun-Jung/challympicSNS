package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Comment;
import com.ssafy.challympic.domain.CommentLike;
import com.ssafy.challympic.domain.Post;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.CommentLikeService;
import com.ssafy.challympic.service.CommentService;
import com.ssafy.challympic.service.PostService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/comment")
    public Result save(@RequestBody CommentRequest request){
        User findUser = userService.findUser(request.user_no);
        Post findPost = postService.getPost(request.post_no);
        Comment comment = new Comment();
        comment.setUser(findUser);
        comment.setPost(findPost);
        comment.setComment_content(request.getComment_content());
        commentService.save(comment);
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


    }
}
