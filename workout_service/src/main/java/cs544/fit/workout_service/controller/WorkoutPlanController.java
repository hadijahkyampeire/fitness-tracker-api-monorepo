package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;
import cs544.fit.workout_service.service.WorkoutPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/workouts")
@Tag(name = "Workouts API", description = "API for managing workouts")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService planService;

    // ACCESSED BY ADMIN AND COACH
    @PostMapping
    @Operation(summary = "Create a workout plan")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(responseCode = "200", description = "Workout plan created")
    public ResponseEntity<WorkoutPlanDTO> createPlan(@RequestBody WorkoutPlanDTO dto) {
        return ResponseEntity.ok(planService.createPlan(dto));
    }

    @GetMapping
    @Operation(summary = "Get all workout plans")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(responseCode = "200", description = "List of all workout plans")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get workout plans by user ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(responseCode = "200", description = "List of workout plans for the user")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(planService.getPlansByUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get workout plan by ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workout plan found"),
            @ApiResponse(responseCode = "404", description = "Workout plan not found")
    })
    public ResponseEntity<WorkoutPlanDTO> getPlan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a workout plan by ID")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Workout plan deleted"),
            @ApiResponse(responseCode = "404", description = "Workout plan not found")
    })
    public ResponseEntity<Void> deletePlan(@PathVariable("id") Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Get workout plans by difficulty level")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(responseCode = "200", description = "Workout plans for specified level")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByLevel(@PathVariable("level") String level) {
        return ResponseEntity.ok(planService.getPlansByLevel(level));
    }

    @GetMapping("/user/{userId}/level/{level}")
    @Operation(summary = "Get workout plans by user and level")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponse(responseCode = "200", description = "Workout plans for user with specified level")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUserAndLevel(
            @PathVariable("userId") Long userId,
            @PathVariable("level") String level
    ) {
        return ResponseEntity.ok(planService.getPlansByUserAndLevel(userId, level));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a workout plan")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workout plan updated"),
            @ApiResponse(responseCode = "403", description = "Access denied or not found")
    })
    public ResponseEntity<?> updatePlan(@PathVariable("id") Long id, @RequestBody WorkoutPlanDTO dto) {
        Optional<WorkoutPlanDTO> result = planService.updatePlan(id, dto);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "Access denied or not found"));
        }
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get workout plans by category")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workout plans found"),
            @ApiResponse(responseCode = "204", description = "No plans found for category")
    })
    public ResponseEntity<List<WorkoutPlanDTO>> getByCategory(@PathVariable("categoryId") Long categoryId) {
        List<WorkoutPlanDTO> list = planService.getPlansByCategory(categoryId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
