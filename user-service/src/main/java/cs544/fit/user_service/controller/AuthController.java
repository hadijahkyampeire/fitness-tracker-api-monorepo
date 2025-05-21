package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.*;
import cs544.fit.user_service.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserWithProfileResponse>> getAllUsersWithProfiles() {
        return ResponseEntity.ok(authService.getAllUsersWithProfiles());
    }

}
