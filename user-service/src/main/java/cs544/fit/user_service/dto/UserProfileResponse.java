package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
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
