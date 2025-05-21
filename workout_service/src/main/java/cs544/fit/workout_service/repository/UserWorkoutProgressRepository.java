package cs544.fit.workout_service.repository;

import cs544.fit.workout_service.entity.UserWorkoutProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserWorkoutProgressRepository extends JpaRepository<UserWorkoutProgress, Long> {
    List<UserWorkoutProgress> findByUserId(Long userId);
    Optional<UserWorkoutProgress> findByUserIdAndWorkoutId(Long userId, Long workoutId);
}
