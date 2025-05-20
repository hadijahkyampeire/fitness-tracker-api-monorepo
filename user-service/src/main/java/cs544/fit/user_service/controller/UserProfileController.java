package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.UserProfileRequest;
import cs544.fit.user_service.dto.UserProfileResponse;
import cs544.fit.user_service.service.impl.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/user")
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> create(@PathVariable("userId") Long userId, @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.createProfile(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> get(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserProfileResponse> updateProfile(@PathVariable("userId") Long userId, @RequestBody UserProfileRequest request) {
        return ResponseEntity.ok(userProfileService.updateProfile(userId, request));
    }
}
