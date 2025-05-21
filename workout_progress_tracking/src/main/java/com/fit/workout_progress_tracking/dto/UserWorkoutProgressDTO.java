package com.fit.workout_progress_tracking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserWorkoutProgressDTO {
    private Long id;
    private Long userId;
    private Long categoryId;
    private Long workoutId;
    private String workoutTitle;
    private String categoryName;
    private String status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}

