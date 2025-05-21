package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.entity.WorkoutPlan;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Workout API", description = "API for managing workouts")
public class WorkoutCategoryController {

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    public String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    // ACCESSED ONLY BY ADMIN
    @PostMapping
    @Operation(summary = "Create a workout category", description = "Creates a new workout category (admin only)")
    @SecurityRequirement(name = "BearerAuth")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"Strength Training\", \"description\": \"Workouts focused on building muscle\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "403", description = "Unauthorized (requires ROLE_ADMIN)"),
            @ApiResponse(responseCode = "409", description = "Category name already exists")
    })
    public ResponseEntity<?> create(@RequestBody WorkoutCategoryDTO dto) {
        try {
            WorkoutCategory category = new WorkoutCategory();
            category.setName(dto.name());
            category.setDescription(dto.description());
            WorkoutCategory saved = categoryRepository.save(category);
            return ResponseEntity.ok(new WorkoutCategoryDTO(saved.getId(), saved.getName(), saved.getDescription()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Category name already exists. Please choose a different name.");
        }
    }

    // ACCESSED ONLY BY ADMIN, COACH AND USER
    @GetMapping
    public ResponseEntity<List<WorkoutCategory>> getAll() {
        List<WorkoutCategory> categories = categoryRepository.findAll().stream()
                .map(c -> new WorkoutCategory(
                        c.getId(),
                        c.getName(),
                        c.getDescription(),
                        c.getWorkoutPlans()
                        ))
                .toList();
        return ResponseEntity.ok(categories);
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/{id}")
    @Operation(summary = "Get all workout categories", description = "Retrieves a list of all workout categories (accessible by admin, coach, user)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categories retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "[{\"id\": 1, \"name\": \"Strength Training\", \"description\": \"Workouts focused on building muscle\", \"workoutPlans\": []}]"
                            )
                    )
            )
    })
    public ResponseEntity<WorkoutCategory> getById(@PathVariable("id") Long id) {
        return categoryRepository.findById(id)
                .map(c -> new WorkoutCategory(c.getId(), c.getName(), c.getDescription(), c.getWorkoutPlans()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ACCESSED ONLY BY ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> update(@PathVariable("id") Long id, @RequestBody WorkoutCategoryDTO dto) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.name());
                    existing.setDescription(dto.description());
                    WorkoutCategory updated = categoryRepository.save(existing);
                    return ResponseEntity.ok(new WorkoutCategoryDTO(updated.getId(), updated.getName(), updated.getDescription()));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ACCESSED ONLY BY ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
