package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.CategorySelectRequestDTO;
import cs544.fit.workout_service.dto.WorkoutProgressUpdateResponse;
import cs544.fit.workout_service.dto.WorkoutStatusUpdateRequestDTO;
import cs544.fit.workout_service.entity.UserWorkoutProgress;
import cs544.fit.workout_service.service.UserWorkoutProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress-tracker")
@RequiredArgsConstructor
@Tag(name = "Progress Tracker API", description = "Track and update user's workout progress")
@SecurityRequirement(name = "BearerAuth")
public class UserWorkoutProgressController {

    private final UserWorkoutProgressService progressService;

    @PostMapping("/create")
    @Operation(summary = "Create workout progress entries for selected categories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Progress created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid category IDs", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public List<UserWorkoutProgress> createProgressForUser(
            @RequestBody CategorySelectRequestDTO request
    ) {
        return progressService.createProgressForCategories(request.getCategoryIds());
    }

    @GetMapping
    @Operation(summary = "Get progress for current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user progress"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public List<UserWorkoutProgress> getMyProgress() {
        return progressService.getUserProgress();
    }

    @PutMapping("/{progressId}")
    @Operation(summary = "Update progress status for a specific progress entry")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Progress updated successfully"),
            @ApiResponse(responseCode = "404", description = "Progress entry not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public WorkoutProgressUpdateResponse updateStatus(
            @PathVariable("progressId") Long progressId,
            @RequestBody WorkoutStatusUpdateRequestDTO status
    ) {
        return progressService.updateProgressStatus(progressId, status);
    }
}
