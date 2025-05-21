package com.fit.workout_progress_tracking.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDTO {
    private long userId;
    private String userName;
    private String userEmail;
    private double weight;
    private double height;
    private List<String> goals;
}
