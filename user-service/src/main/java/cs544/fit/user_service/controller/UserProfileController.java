package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.UserProfileRequest;
import cs544.fit.user_service.dto.UserProfileResponse;
import cs544.fit.user_service.service.impl.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/user")
@Tag(name = "User Profile API", description = "API for managing user profiles")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/{userId}")
    @Operation(summary = "Create user profile", description = "Creates a profile for a user with the specified user ID")
    @SecurityRequirement(name = "BearerAuth") // Indicates JWT required
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_USER)")
    })
    public ResponseEntity<UserProfileResponse> create(@PathVariable("userId") Long userId, @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.createProfile(userId, request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile", description = "Retrieves the profile of a user by user ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_USER)"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<UserProfileResponse> get(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Updates the profile of a user with the specified user ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_USER)"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(userId, request));
    }
}
