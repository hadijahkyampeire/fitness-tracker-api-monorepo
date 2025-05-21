package com.fit.workout_progress_tracking.service.impl;

import com.fit.workout_progress_tracking.entity.UserFeedBack;
import com.fit.workout_progress_tracking.repositories.UserFeedBackRepository;
import com.fit.workout_progress_tracking.service.UserFeedBackService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserFeedBackImpl implements UserFeedBackService {
    @Autowired
    private UserFeedBackRepository ufeedRepo;

    @Override
    public List<UserFeedBack> findByUserId(String userId) {
        return List.of();
    }

    @Override
    public void addFeedBack(UserFeedBack userFeedBack) {

    }
}
