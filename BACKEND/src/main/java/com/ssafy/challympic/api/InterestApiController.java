package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Interest;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.InterestService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class InterestApiController {

    private final InterestService interestService;
    private final UserService userService;

    @PostMapping("/user/interest/{userNo}")
    public Result save(@PathVariable("userNo") int user_no, @RequestBody interestRequest request){
        boolean saveOk = interestService.save(user_no, request.getTag_no());
        List<Interest> interestList = interestService.findByUser(user_no);
        List<InterestDto> interests = new ArrayList<>();
        if(!interestList.isEmpty()) {
            interests = interestList.stream()
                    .map(i -> new InterestDto(i))
                    .collect(Collectors.toList());
        }

        if(saveOk){
            return new Result(true, HttpStatus.OK.value(), interests);
        }else{
            return new Result(false, HttpStatus.BAD_REQUEST.value());
        }
    }

    @Data
    static class JoinAfterInterest{
        private String user_email;
        private List<Integer> interests;
    }

    @PostMapping("/setInterests")
    public Result saves(@RequestBody JoinAfterInterest request){

        User user = userService.findByEmail(request.getUser_email());

        for (Integer t : request.getInterests()) {
            interestService.save(user.getUser_no(), t);
        }
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/interest/{userNo}")
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
        List<Interest> interestList = interestService.findByUser(user_no);
        List<InterestDto> interests = new ArrayList<>();
        if(!interestList.isEmpty()) {
            interests = interestList.stream()
                    .map(i -> new InterestDto(i))
                    .collect(Collectors.toList());
        }
        return new Result(true, HttpStatus.OK.value(), interests);
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

}
