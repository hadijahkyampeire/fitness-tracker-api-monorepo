package cs544.fit.workout_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object for workout category")
public record WorkoutCategoryDTO(
        @Schema(description = "Category ID", example = "1") Long id,
        @Schema(description = "Category name", example = "Strength Training") String name,
        @Schema(description = "Category description", example = "Workouts focused on building muscle") String description
) {}
