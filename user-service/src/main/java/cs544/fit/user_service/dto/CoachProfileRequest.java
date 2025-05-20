package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.Data;

@Data
public class CoachProfileRequest {
    private int age;
    private Gender gender;
    private String qualification;
    private String bio;
}
