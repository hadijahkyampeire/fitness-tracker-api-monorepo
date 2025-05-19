package cs544.fit.workout_service.repository;

import cs544.fit.workout_service.entity.WorkoutCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutCategoryRepository extends JpaRepository<WorkoutCategory, Long> {

    Optional<WorkoutCategory> findByName(String name);
}
