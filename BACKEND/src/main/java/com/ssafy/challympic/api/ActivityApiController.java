package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.Activity;
import com.ssafy.challympic.domain.Result;
import com.ssafy.challympic.domain.Tag;
import com.ssafy.challympic.domain.User;
import com.ssafy.challympic.service.ActivityService;
import com.ssafy.challympic.service.TagService;
import com.ssafy.challympic.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ActivityApiController {

    private final ActivityService activityService;
    private final TagService tagService;
    private final UserService userService;

    @PostMapping("/activity")
    public Result setActivity(@RequestBody ActivityRequest request) {
        Activity activity = new Activity();
        activity.setPost_no(request.getPostNo());
        activity.setUser_no(request.getUserNo());

//        List<Tag> tagList = tagService.findPostTagListByPostNo(request.getPostNo());

        activityService.saveActivity(activity);
        return new Result(true, HttpStatus.OK.value());
    }

    @GetMapping("/activity/{userNo}")
    public Result getActivity(@PathVariable int userNo) {
        List<Activity> mainActivityList = activityService.findActivityListByUserNo(userNo);
        Map<Integer, List<Activity>> activityMap = new HashMap<>();
        List<User> allUser = userService.findAllUser();
        for(User user : allUser) {
            List<Activity> activityList = activityService.findActivityListByUserNo(user.getUser_no());
            activityMap.put(user.getUser_no(), activityList);
        }
        activityMap.forEach((user_no, activity) -> {
            /*
            1. 같은 포스트를 확인한 사용자만을 대상으로 한다면 결국 비슷한 태그만 나올 수 밖에 없음.
            2. 결국 순서를 확인하는 것이 그나마 나을 수 있을 수도.
            3. 순서를 확인할 때 안나오면 넘어갈지
            4. 알고리즘을 어떻게 구성할지에 대해 생각해보아야함.
             */

        });



        return null;
    }

    @Data
    static class ActivityRequest {
        private int postNo;
        private int userNo;
    }
}
