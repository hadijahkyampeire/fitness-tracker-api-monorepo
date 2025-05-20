package cs544.fit.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithProfileResponse {
    private Long id;
    private String username;
    private String email;
    private String role;
    private UserProfileResponse userProfile; // Null for coaches
    private CoachProfileResponse coachProfile; // Null for regular users
}
