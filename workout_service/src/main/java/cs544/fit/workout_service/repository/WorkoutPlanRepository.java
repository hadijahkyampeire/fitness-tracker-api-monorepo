package cs544.fit.workout_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cs544.fit.workout_service.entity.WorkoutPlan;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    List<WorkoutPlan> findByLevel(String level);

    List<WorkoutPlan> findByUserId(Long userId);

    List<WorkoutPlan> findByUserIdAndLevel(Long userId, String level);
}
