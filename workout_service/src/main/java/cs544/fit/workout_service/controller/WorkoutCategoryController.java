package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutCategoryDTO;
import cs544.fit.workout_service.service.WorkoutCategoryService;
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
    private WorkoutCategoryService workoutCategoryService; // Inject service instead of repo

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
            WorkoutCategoryDTO saved = workoutCategoryService.createCategory(dto);
            return ResponseEntity.ok(saved);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).body("Category name already exists. Please choose a different name.");
        }
    }

    // ACCESSED ONLY BY ADMIN, COACH AND USER
    @GetMapping
    public ResponseEntity<List<WorkoutCategoryDTO>> getAll() {
        List<WorkoutCategoryDTO> categories = workoutCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/{id}")
    @Operation(summary = "Get workout category by ID", description = "Retrieves a workout category by ID (accessible by admin, coach, user)")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Category retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{\"id\": 1, \"name\": \"Strength Training\", \"description\": \"Workouts focused on building muscle\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<WorkoutCategoryDTO> getById(@PathVariable("id") Long id) {
        try {
            WorkoutCategoryDTO category = workoutCategoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ACCESSED ONLY BY ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutCategoryDTO> update(@PathVariable("id") Long id, @RequestBody WorkoutCategoryDTO dto) {
        try {
            WorkoutCategoryDTO updated = workoutCategoryService.updateCategory(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ACCESSED ONLY BY ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            workoutCategoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
