package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.*;
import cs544.fit.user_service.service.IAuthService;
import cs544.fit.user_service.service.impl.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "User Authentication API", description = "API for user management")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/register-user")
    @Operation(summary = "Register a new user", description = "Creates a new user account with USER role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/register-coach")
    @Operation(summary = "Register a new coach", description = "Creates a new user account with COACH role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Coach registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<RegisterResponse> registerCoach(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerCoach(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Authenticates a user and returns JWT tokens")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful login",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Admin Login",
                                            summary = "Login as an admin (ROLE_ADMIN)",
                                            value = "{\"email\": \"admin@example.com\", \"password\": \"admin123\"}"
                                    ),
                                    @ExampleObject(
                                            name = "User Login",
                                            summary = "Login as a user (ROLE_USER)",
                                            value = "{\"email\": \"user@example.com\", \"password\": \"user123\"}"
                                    ),
                                    @ExampleObject(
                                            name = "Coach Login",
                                            summary = "Login as a coach (ROLE_COACH)",
                                            value = "{\"email\": \"coach@example.com\", \"password\": \"coach123\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users with profiles", description = "Retrieves a list of all users with their profiles (admin access required)")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<List<UserWithProfileResponse>> getAllUsersWithProfiles() {
        return ResponseEntity.ok(authService.getAllUsersWithProfiles());
    }

}
