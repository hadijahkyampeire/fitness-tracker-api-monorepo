package cs544.fit.workout_service.dto;

import cs544.fit.workout_service.entity.WorkoutStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutProgressUpdateResponse {
    private Long workoutId;
    private Long categoryId;
    private WorkoutStatus status;
}
