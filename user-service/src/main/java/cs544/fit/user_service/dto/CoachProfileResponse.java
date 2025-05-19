package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class CoachProfileResponse {
    private Long id;
    private int age;
    private Gender gender;
    private String qualifications;
    private String bio;

    private Long userId;
    private String userEmail;
    private String username;
    private String role;

    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
}
