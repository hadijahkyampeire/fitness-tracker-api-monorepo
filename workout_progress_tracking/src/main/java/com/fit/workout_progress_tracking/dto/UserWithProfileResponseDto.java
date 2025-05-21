package com.fit.workout_progress_tracking.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserWithProfileResponseDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private UserProfileResponse userProfile; // Null for coaches
    private CoachProfileResponse coachProfile;
}
