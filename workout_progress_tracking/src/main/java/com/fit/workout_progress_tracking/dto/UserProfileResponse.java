package com.fit.workout_progress_tracking.dto;

import com.fit.workout_progress_tracking.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UserProfileResponse {
    private Long id;
    private double weight;
    private double height;
    private int age;
    private Gender gender;
    private String medicalConditions;
    private Double bmi;

    private Long userId;
    private String userEmail;
    private String username;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}

