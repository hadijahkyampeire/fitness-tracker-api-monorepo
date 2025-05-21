package cs544.fit.workout_service.dto;

import lombok.Data;

@Data
public class UserWorkoutProgressDTO {
    private Long userId;
    private String workoutTitle;
    private String status;
    private Long categoryId;
    private String categoryName;
}
