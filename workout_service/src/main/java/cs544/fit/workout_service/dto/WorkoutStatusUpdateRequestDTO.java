package cs544.fit.workout_service.dto;

import cs544.fit.workout_service.entity.WorkoutStatus;
import lombok.Data;

@Data
public class WorkoutStatusUpdateRequestDTO {
    WorkoutStatus status;
}
