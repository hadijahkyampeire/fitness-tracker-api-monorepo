package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.entity.WorkoutPlan;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import cs544.fit.workout_service.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository planRepository;

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    @Override
    public WorkoutPlanDTO createPlan(WorkoutPlanDTO dto) {
        WorkoutPlan plan = new WorkoutPlan();
        plan.setUserId(dto.userId());
        plan.setTitle(dto.title());
        plan.setDescription(dto.description());
        plan.setEstimatedCalories(dto.estimatedCalories());
        plan.setDurationMinutes(dto.durationMinutes());
        plan.setLevel(dto.level());

        if ( dto.categoryId() != null) {
            WorkoutCategory category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            plan.setCategory(category);
        }

        WorkoutPlan saved = planRepository.save(plan);
        return toDTO(saved);
    }

    @Override
    public List<WorkoutPlanDTO> getPlansByUser(Long userId) {
        return planRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutPlanDTO> getPlansByLevel(String level) {
        return planRepository.findByLevel(level).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutPlanDTO> getPlansByUserAndLevel(Long userId, String level) {
        return planRepository.findByUserIdAndLevel(userId, level).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutPlanDTO getPlan(Long id) {
        WorkoutPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        return toDTO(plan);
    }

    @Override
    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    private WorkoutPlanDTO toDTO(WorkoutPlan plan) {
        return new WorkoutPlanDTO(
                plan.getId(),
                plan.getUserId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.getEstimatedCalories(),
                plan.getDurationMinutes(),
                plan.getLevel(),
                plan.getCategory() != null ? plan.getCategory().getId() : null
        );
    }
}
