package com.fit.workout_progress_tracking.repositories;

import com.fit.workout_progress_tracking.entity.UserFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFeedBackRepository extends JpaRepository<UserFeedBack, Long> {
    List<UserFeedBack> findByUserId(Long userId);
}
