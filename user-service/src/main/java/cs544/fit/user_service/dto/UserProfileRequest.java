package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileRequest {
    private int age;
    private double height;
    private double weight;
    private Gender gender;
    private String medicalConditions;
}
