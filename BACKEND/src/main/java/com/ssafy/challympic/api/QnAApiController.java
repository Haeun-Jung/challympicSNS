package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.QnA;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.QnAService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class QnAApiController {

    private final QnAService qnAService;
    private final UserService userService;

    @PostMapping("/user/{userNo}/qna")
    public Result question(@PathVariable("userNo") int user_no, @RequestBody QnARequest request){
        User user = userService.findUser(user_no);
        QnA qnA = new QnA();
        qnA.setUser(user);
        qnA.setQna_title(request.getQna_title());
        qnA.setQna_question(request.getQna_question());
        qnAService.save(qnA);
        Result collect = qnaList(user_no);
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @GetMapping("/user/{userNo}/qna")
    public Result qnaList(@PathVariable("userNo") int user_no){
        List<QnA> qnaList = qnAService.findByUser(user_no);
        List<QnADto> collect = qnaList.stream()
                .map(q ->
                        new QnADto(q))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @Data
    static class QnARequest{
        private String qna_title;
        private String qna_question;
    }

    @Data
    @AllArgsConstructor
    static class QnADto{
        private int qna_no;
        private String user_nickname;
        private String qna_title;
        private String qna_question;
        private String qna_answer;
        private Date qna_question_regdate;
        private Date qna_answer_regdate;

        public QnADto(QnA qna) {
            this.qna_no = qna.getQna_no();
            this.user_nickname = qna.getUser().getUser_nickname();
            this.qna_title = qna.getQna_title();
            this.qna_question = qna.getQna_question();
            this.qna_answer = qna.getQna_answer();
            this.qna_question_regdate = qna.getQna_question_regdate();
            this.qna_answer_regdate = qna.getQna_answer_regdate();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            this.qna_question_regdate = formatter.format(qna.getQna_question_regdate());
//            this.qna_answer_regdate = formatter.format(qna.getQna_answer_regdate());

        }
    }

}
