package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserProfileRequest {
    private LocalDate dateOfBirth;
    private double height;
    private double weight;
    private Gender gender;
    private String medicalConditions;
}
