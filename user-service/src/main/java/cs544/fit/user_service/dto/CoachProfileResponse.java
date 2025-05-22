package cs544.fit.user_service.dto;

import cs544.fit.user_service.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
