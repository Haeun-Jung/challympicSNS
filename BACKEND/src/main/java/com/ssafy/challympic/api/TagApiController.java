package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagApiController {

    private final TagService tagService;

    /**
     * 개인 정보 태그 추가
     */
    @PostMapping("/user/{userNo}/tag")
    public void userTag(@PathVariable("userNo") int user_no, @RequestBody List<Integer> request){
        request.forEach(tag_no -> {
            Tag findTag = tagService.findOne(tag_no);
//            interestService
        });
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

    static class tagRequest {
        private List<Integer> tag_noList;
    }
}
