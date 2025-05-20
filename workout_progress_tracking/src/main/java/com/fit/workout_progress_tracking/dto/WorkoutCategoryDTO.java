package com.fit.workout_progress_tracking.dto;

import java.util.List;

public record WorkoutCategoryDTO(
        Long id,
        String name,
        String description,
        List<WorkoutPlanDTO> workoutPlanDTOS
) {}
