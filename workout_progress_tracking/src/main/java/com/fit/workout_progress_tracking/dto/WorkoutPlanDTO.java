package com.fit.workout_progress_tracking.dto;

public record WorkoutPlanDTO(
        Long id,
        Long userId,
        String title,
        String description,
        int estimatedCalories,
        int durationMinutes,
        String level,
        Long categoryId
) {}
