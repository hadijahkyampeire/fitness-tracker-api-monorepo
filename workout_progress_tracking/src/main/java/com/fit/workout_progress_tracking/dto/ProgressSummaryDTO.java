package com.fit.workout_progress_tracking.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProgressSummaryDTO {
    private Long userId;
    private String fullName;
    private String email;
    private int age;
    private double weight;
    private double height;
    private Double bmi;
    private String gender;
    private String role;
    private String medicalConditions;

    private int totalWorkouts;
    private int completed;
    private int notStarted;
    private int inProgress;

    private List<CategoryProgressDTO> categoryProgress;
}

