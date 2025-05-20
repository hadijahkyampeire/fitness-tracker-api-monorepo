package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;
import cs544.fit.workout_service.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService planService;

    // ACCESSED BY ADMIN AND COACH
    @PostMapping
    public ResponseEntity<WorkoutPlanDTO> createPlan(@RequestBody WorkoutPlanDTO dto) {
        return ResponseEntity.ok(planService.createPlan(dto));
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping
    public ResponseEntity<List<WorkoutPlanDTO>> getPlans() {
        return ResponseEntity.ok(planService.getAllPlans());
    }

    // ACCESSED ONLY BY ADMIN AND USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUser(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(planService.getPlansByUser(userId));
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> getPlan(@PathVariable("id") Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    // ACCESSED ONLY BY ADMIN AND COACH
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable("id") Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/level/{level}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByLevel(@PathVariable("level") String level) {
        return ResponseEntity.ok(planService.getPlansByLevel(level));
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/user/{userId}/level/{level}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUserAndLevel(
            @PathVariable("userId") Long userId,
            @PathVariable("level") String level
    ) {
        return ResponseEntity.ok(planService.getPlansByUserAndLevel(userId, level));
    }

    // ACCESSED BY ADMIN AND COACH
    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> updatePlan(@PathVariable("id") Long id, @RequestBody WorkoutPlanDTO dto) {
        return planService.updatePlan(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ACCESSED BY ADMIN, COACH AND USER
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<WorkoutPlanDTO>> getByCategory(
            @PathVariable("categoryId") Long categoryId) {
        List<WorkoutPlanDTO> list = planService.getPlansByCategory(categoryId);
        if (list.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }
}
