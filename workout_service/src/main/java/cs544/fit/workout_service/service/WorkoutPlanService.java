package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;

import java.util.List;

public interface WorkoutPlanService {
    WorkoutPlanDTO createPlan(WorkoutPlanDTO dto);
    List<WorkoutPlanDTO> getPlansByUser(Long userId);
    List<WorkoutPlanDTO> getPlansByLevel(String level);
    List<WorkoutPlanDTO> getPlansByUserAndLevel(Long userId, String level);
    WorkoutPlanDTO getPlan(Long id);
    void deletePlan(Long id);
}
