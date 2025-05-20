package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.WorkoutPlanDTO;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.entity.WorkoutPlan;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import cs544.fit.workout_service.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository planRepository;

    @Autowired
    private WorkoutCategoryRepository categoryRepository;

    private Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaim("userId");
        }
        throw new IllegalStateException("User not authenticated or JWT missing");
    }

    private boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }

    @Override
    public WorkoutPlanDTO createPlan(WorkoutPlanDTO dto) {
        Long userId = getLoggedInUserId();

        WorkoutPlan plan = new WorkoutPlan();
        plan.setUserId(userId); // always set from JWT, ignore dto.userId()
        plan.setTitle(dto.title());
        plan.setDescription(dto.description());
        plan.setEstimatedCalories(dto.estimatedCalories());
        plan.setDurationMinutes(dto.durationMinutes());
        plan.setLevel(dto.level());

        if (dto.categoryId() != null) {
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
        Long userId = getLoggedInUserId();
        boolean isAdmin = isAdmin();

        WorkoutPlan plan = planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // Only the creator (coach) or an admin can delete
        if (!isAdmin && !plan.getUserId().equals(userId)) {
            throw new SecurityException("Unauthorized to delete this workout plan");
        }

        planRepository.deleteById(id);
    }


    @Override
    public List<WorkoutPlanDTO> getAllPlans() {
        return planRepository.findAll().stream()
                .map(this::toDTO).toList();
    }

    @Override
    public Optional<WorkoutPlanDTO> updatePlan(Long id, WorkoutPlanDTO dto) {
        Long userId = getLoggedInUserId();
        boolean isAdmin = isAdmin();

        return planRepository.findById(id)
                .map(existingPlan -> {
                    // Optional: Allow only owner or admin to update
                    if (!isAdmin && !existingPlan.getUserId().equals(userId)) {
                        throw new SecurityException("Unauthorized to update this plan");
                    }

                    existingPlan.setUserId(existingPlan.getUserId()); // preserve ownership
                    existingPlan.setTitle(dto.title());
                    existingPlan.setDescription(dto.description());
                    existingPlan.setEstimatedCalories(dto.estimatedCalories());
                    existingPlan.setDurationMinutes(dto.durationMinutes());
                    existingPlan.setLevel(dto.level());

                    if (dto.categoryId() != null) {
                        WorkoutCategory category = categoryRepository.findById(dto.categoryId())
                                .orElse(null);
                        existingPlan.setCategory(category);
                    } else {
                        existingPlan.setCategory(null);
                    }

                    WorkoutPlan updated = planRepository.save(existingPlan);
                    return toDTO(updated);
                });
    }

    @Override
    public List<WorkoutPlanDTO> getPlansByCategory(Long categoryId) {
        return planRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(this::toDTO)
                .toList();
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
