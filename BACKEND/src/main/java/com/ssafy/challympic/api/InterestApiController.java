package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Interest;
import com.ssafy.challympic.service.InterestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class InterestApiController {

    private final InterestService interestService;

    @PostMapping("/user/interest/{userNo}")
    public Result save(@PathVariable("userNo") int user_no, @RequestBody interestRequest request){
        interestService.save(user_no, request.getTag_no());
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/user/interest/{userNo}")
    public Result interestList(@PathVariable("userNo") int user_no){
        List<Interest> interestList = interestService.findByUser(user_no);
        List<InterestDto> collect = interestList.stream()
                .map(m -> new InterestDto(m))
                .collect(Collectors.toList());
        return new Result(true, HttpStatus.OK.value(), collect);
    }

    @DeleteMapping("/user/interest/{userNo}/{tagNo}")
    public Result delete(@PathVariable("userNo") int user_no, @PathVariable("tagNo") int tag_no){
        interestService.deleteInterest(user_no, tag_no);
        return new Result(true, HttpStatus.OK.value());
    }

    @Data
    static class interestRequest{
        private int tag_no;
    }

    @Data
    @AllArgsConstructor
    static class InterestDto{
        private int interest_no;
        private int tag_no;
        private String tag_content;

        public InterestDto(Interest interest) {
            this.interest_no = interest.getInterest_no();
            this.tag_no = interest.getTag().getTag_no();
            this.tag_content = interest.getTag().getTag_content();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private boolean isSuccess;
        private int code;
        private T data;

        public Result(boolean isSuccess, int code) {
            this.isSuccess = isSuccess;
            this.code = code;
        }
    }
}
