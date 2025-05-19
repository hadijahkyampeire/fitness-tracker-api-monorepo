package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanService {
    WorkoutPlanDTO createPlan(WorkoutPlanDTO dto);
    List<WorkoutPlanDTO> getPlansByUser(Long userId);
    List<WorkoutPlanDTO> getPlansByLevel(String level);
    List<WorkoutPlanDTO> getPlansByUserAndLevel(Long userId, String level);
    WorkoutPlanDTO getPlan(Long id);
    List<WorkoutPlanDTO> getAllPlans();
    Optional<WorkoutPlanDTO> updatePlan(Long id, WorkoutPlanDTO dto);
    List<WorkoutPlanDTO> getPlansByCategory(Long categoryId);
    void deletePlan(Long id);
}
