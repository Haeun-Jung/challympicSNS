package com.ssafy.challympic.service;

import com.ssafy.challympic.domain.Activity;
import com.ssafy.challympic.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public void saveActivity(Activity activity) {
        activityRepository.saveActivity(activity);
    }

    public List<Activity> findActivityListByUserNo(int userNo) {
        return activityRepository.findActivityListByUserNo(userNo);
    }
}
