package cs544.fit.workout_service.repository;

import cs544.fit.workout_service.entity.WorkoutCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutCategoryRepository extends JpaRepository<WorkoutCategory, Long> {

    Optional<WorkoutCategory> findByName(String name);

//    @Query("SELECT wc.name FROM WorkoutCategory wc JOIN wc.workoutPlans wp WHERE wc.id = :id AND wc.id=wp.id")
//    public List<WorkoutCategory> getCategoriesWithPlans(Long id);
}
