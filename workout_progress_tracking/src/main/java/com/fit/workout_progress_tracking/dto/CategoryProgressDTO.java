package com.fit.workout_progress_tracking.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryProgressDTO {
    private Long categoryId;
    private String categoryName;

    private int totalWorkouts;
    private int notStarted;
    private int inProgress;
    private int completed;

    private List<String> workoutTitles;

    // Getters, setters, constructor
}
