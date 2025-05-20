package cs544.fit.workout_service.dto;

import cs544.fit.workout_service.entity.WorkoutPlan;

import java.util.List;

public record WorkoutCategoryDTO(
        Long id,
        String name,
        String description,
        List<String> workoutPlanNames
) {}
