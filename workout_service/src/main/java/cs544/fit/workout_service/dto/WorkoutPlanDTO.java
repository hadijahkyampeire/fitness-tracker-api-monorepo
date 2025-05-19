package cs544.fit.workout_service.dto;

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
