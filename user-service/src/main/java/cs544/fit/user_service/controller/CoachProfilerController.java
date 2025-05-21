package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.CoachProfileRequest;
import cs544.fit.user_service.dto.CoachProfileResponse;
import cs544.fit.user_service.service.impl.CoachProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/coach")
@Tag(name = "Coach Profile API", description = "API for managing coach profiles")
public class CoachProfilerController {
    @Autowired
    private CoachProfileService coachProfileService;

    @PostMapping("/{userId}")
    @Operation(summary = "Create coach profile", description = "Creates a profile for a coach with the specified user ID")
    @SecurityRequirement(name = "BearerAuth") // Indicates JWT required
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_COACH)")
    })
    public ResponseEntity<CoachProfileResponse> create(@PathVariable("userId") Long userId, @RequestBody CoachProfileRequest request) {
        return ResponseEntity.ok(coachProfileService.createProfile(userId, request));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get coach profile", description = "Retrieves the profile of a coach by user ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_COACH)"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<CoachProfileResponse> get(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(coachProfileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update coach profile", description = "Updates the profile of a coach with the specified user ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_COACH)"),
            @ApiResponse(responseCode = "404", description = "Profile not found")
    })
    public ResponseEntity<CoachProfileResponse> updateProfile(@PathVariable("userId") Long userId, @RequestBody CoachProfileRequest request) {
        return ResponseEntity.ok(coachProfileService.updateProfile(userId, request));
    }
}
