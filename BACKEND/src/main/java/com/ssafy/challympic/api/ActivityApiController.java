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

        List<Tag> tagList = tagService.findPostTagListByPostNo(request.getPostNo());
        activity.setTagList(tagList);

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
        }
        return null;
    }

    @Data
    static class ActivityRequest {
        private int postNo;
        private int userNo;
    }
}
