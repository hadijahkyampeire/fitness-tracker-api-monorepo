package cs544.fit.workout_service.controller;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;
import cs544.fit.workout_service.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
public class WorkoutPlanController {

    private final WorkoutPlanService planService;

    public WorkoutPlanController(WorkoutPlanService planService) {
        this.planService = planService;
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanDTO> createPlan(@RequestBody WorkoutPlanDTO dto) {
        return ResponseEntity.ok(planService.createPlan(dto));
    }

    @GetMapping
    public List<WorkoutPlanDTO> getUserPlans(@RequestParam Long userId) {
        return planService.getPlansByUser(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanDTO> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok("Workout plan deleted");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUser(@PathVariable Long userId) {
        List<WorkoutPlanDTO> plans = planService.getPlansByUser(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByLevel(@PathVariable String level) {
        List<WorkoutPlanDTO> plans = planService.getPlansByLevel(level);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/user/{userId}/level/{level}")
    public ResponseEntity<List<WorkoutPlanDTO>> getPlansByUserAndLevel(
            @PathVariable Long userId,
            @PathVariable String level
    ) {
        List<WorkoutPlanDTO> plans = planService.getPlansByUserAndLevel(userId, level);
        return ResponseEntity.ok(plans);
    }
}
