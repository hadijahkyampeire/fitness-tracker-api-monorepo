package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.RegisterRequest;
import cs544.fit.user_service.dto.RegisterResponse;
import cs544.fit.user_service.service.IAuthService;
import cs544.fit.user_service.service.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/register-user")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register-coach")
    public ResponseEntity<RegisterResponse> registerCoach(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerCoach(request));
    }

}
