package com.ssafy.challympic.api;

import com.ssafy.challympic.domain.*;
import com.ssafy.challympic.service.ActivityService;
import com.ssafy.challympic.service.TagService;
import com.ssafy.challympic.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

        activityService.saveActivity(activity);
        return new Result(true, HttpStatus.OK.value());
    }

    @Data
    static class TagDto{
        private int tag_no;
        private String tag_content;

        public TagDto(Tag tag) {
            this.tag_no = tag.getTag_no();
            this.tag_content = tag.getTag_content();
        }
    }

    @GetMapping("/activity/{userNo}")
    public Result getActivity(@PathVariable int userNo) {

//        List<Activity> mainUserActivityList = activityService.findActivityListByUserNo(userNo);

//        Map<Integer, List<Activity>> activityMap = new HashMap<>();
//        List<User> allUserList = userService.findAllUser();
//        int[][] intersectionCount = new int[allUserList.size()][2];
//        for(User user : allUserList) {
//            List<Activity> activityList = activityService.findActivityListByUserNo(user.getUser_no());
//            activityMap.put(user.getUser_no(), activityList);
//        }

        // 최신 태그 5개 불러오기
        List<Tag> allTagList = tagService.findRecentAllTagList();
        if(!allTagList.isEmpty()){
            allTagList.removeIf(t -> t.getIsChallenge() != null);
        }else{
            return new Result(true, HttpStatus.OK.value(), null);
        }

        for(Tag t : allTagList) System.out.println(t.getTag_content());

        if(userNo == 0){
            System.out.println("userNo가 0");
            List<TagDto> tagDtos = new ArrayList<>();
            if(allTagList.size() == 0){
                System.out.println("태그가 없어");
                return new Result(true, HttpStatus.OK.value(), null);
            }
            System.out.println("태그가 있어");

            for (Tag tag : allTagList) {
                tagDtos.add(new TagDto(tag));
            }

            if(tagDtos.size() < 5){
                System.out.println("태그가 5개보다 적어");
                return new Result(true, HttpStatus.OK.value(), new ActivityResponse(tagDtos));
            }else{
                System.out.println("태그가 5개보다 많아");
                return new Result(true, HttpStatus.OK.value(), new ActivityResponse(tagDtos.subList(0,5)));
            }
        }else {
            List<Tag> tagResponse = getTagsVer01(userNo);
            tagResponse.removeIf(t -> t.getIsChallenge() != null);
            List<TagDto> tagDtoResponse = new ArrayList<>();

            if (!tagResponse.isEmpty()) {
                tagDtoResponse = tagResponse.stream()
                        .map(t -> new TagDto(t))
                        .collect(Collectors.toList());
                for (TagDto t : tagDtoResponse) System.out.println(t.tag_content);
                return new Result(true, HttpStatus.OK.value(), new ActivityResponse(tagDtoResponse));
            } else {
                return new Result(true, HttpStatus.OK.value(), null);
            }
        }

    }

    private List<Tag> getTagsVer01(int userNo) {
        List<Activity> activityList = activityService.findActivityListByUserNo(userNo);
        List<Tag> tagAll = new ArrayList<>();
        for(Activity activity : activityList) {
            List<PostTag> postTagList = tagService.findPostTagList(activity.getPost_no());
            for(PostTag postTag : postTagList) {
                if(postTag.getTag().getIsChallenge() == null){
                    tagAll.add(postTag.getTag());
                }
            }
        }
        List<Tag> tempTagList = tagService.findAllTagList();
        int maxTag = tempTagList.size();
        int[][] tagCount = new int[maxTag][2];

        for(int i = 0; i < maxTag; i++) tagCount[i][0] = i + 1;
        for(Tag tag : tagAll) {
            tagCount[tag.getTag_no()][1]++;
        }

        Arrays.sort(tagCount, (int[] o1, int[] o2) -> {
            return o1[1] - o2[1];
        });

        List<Tag> tagResponse = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            tagResponse.add(tagService.findOne(tagCount[i][0]));
        }
        return tagResponse;
    }

    @Data
    @AllArgsConstructor
    static class ActivityResponse {
        List<TagDto> tagList;
    }

    @Data
    static class ActivityRequest {
        private int postNo;
        private int userNo;
    }
}
