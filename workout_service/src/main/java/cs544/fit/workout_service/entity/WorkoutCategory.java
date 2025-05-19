package cs544.fit.workout_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "workout_categories")
@Data
public class WorkoutCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "Cardio", "Strength", "Flexibility"
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<WorkoutPlan> workoutPlans;
}
