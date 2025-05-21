package com.fit.workout_progress_tracking.dto;

import lombok.Data;

@Data
public class UserRequestFeedBackDTO {
    private double newWeight;
    private String comment;
}
