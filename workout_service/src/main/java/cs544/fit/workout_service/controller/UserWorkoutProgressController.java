package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.CategorySelectRequestDTO;
import cs544.fit.workout_service.dto.WorkoutProgressUpdateResponse;
import cs544.fit.workout_service.dto.WorkoutStatusUpdateRequestDTO;
import cs544.fit.workout_service.entity.UserWorkoutProgress;
import cs544.fit.workout_service.entity.WorkoutStatus;
import cs544.fit.workout_service.service.UserWorkoutProgressService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress-tracker")
@RequiredArgsConstructor

public class UserWorkoutProgressController {

    private final UserWorkoutProgressService progressService;

    @PostMapping("/create")
    public List<UserWorkoutProgress> createProgressForUser(
            @RequestBody CategorySelectRequestDTO request
    ) {
        return progressService.createProgressForCategories(request.getCategoryIds());
    }

    @GetMapping
    public List<UserWorkoutProgress> getMyProgress() {
        return progressService.getUserProgress();
    }

    @PutMapping("/{progressId}")
    public WorkoutProgressUpdateResponse updateStatus(@PathVariable("progressId") Long progressId, @RequestBody WorkoutStatusUpdateRequestDTO status) {
        return progressService.updateProgressStatus(progressId, status);
    }
}

