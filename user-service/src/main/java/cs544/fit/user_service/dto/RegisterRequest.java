package cs544.fit.user_service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
//    @Column(unique = true)
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;
}
