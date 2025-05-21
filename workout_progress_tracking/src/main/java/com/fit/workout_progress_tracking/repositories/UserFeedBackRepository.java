package com.fit.workout_progress_tracking.repositories;

import com.fit.workout_progress_tracking.entity.UserFeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFeedBackRepository extends JpaRepository<UserFeedBack, Long> {
}
