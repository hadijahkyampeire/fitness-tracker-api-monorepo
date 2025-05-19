package cs544.fit.workout_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int estimatedCalories;
    private int durationMinutes;
    private String level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long userId; // owner of the plan -- coach

    @ManyToOne
    @JoinColumn(name = "category_id")
    private WorkoutCategory category;

// REPORTS TO BE ADDED BY JAMAL
//    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL)
//    private List<WorkoutLog> logs = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

