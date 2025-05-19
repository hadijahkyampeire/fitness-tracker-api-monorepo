package cs544.fit.user_service.controller;

import cs544.fit.user_service.dto.CoachProfileRequest;
import cs544.fit.user_service.dto.CoachProfileResponse;
import cs544.fit.user_service.service.impl.CoachProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile/coach")
public class CoachProfilerController {
    @Autowired
    private CoachProfileService coachProfileService;

    @PostMapping("/{userId}")
    public ResponseEntity<CoachProfileResponse> create(@PathVariable("userId") Long userId, @RequestBody CoachProfileRequest request) {
        return ResponseEntity.ok(coachProfileService.createProfile(userId, request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CoachProfileResponse> get(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(coachProfileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CoachProfileResponse> updateProfile(@PathVariable("userId") Long userId, @RequestBody CoachProfileRequest request) {
        return ResponseEntity.ok(coachProfileService.updateProfile(userId, request));
    }
}
