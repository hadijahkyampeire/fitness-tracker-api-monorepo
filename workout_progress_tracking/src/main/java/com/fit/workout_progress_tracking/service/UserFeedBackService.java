package com.fit.workout_progress_tracking.service;

import com.fit.workout_progress_tracking.dto.UserRequestFeedBackDTO;
import com.fit.workout_progress_tracking.entity.UserFeedBack;

import java.util.List;

public interface UserFeedBackService {
    public List<UserFeedBack> findByUserId();
    public UserFeedBack addFeedBack(UserRequestFeedBackDTO userFeedBack);
}
