package cs544.fit.workout_service.service;

import cs544.fit.workout_service.config.JwtUtil;
import cs544.fit.workout_service.dto.UserWorkoutProgressDTO;
import cs544.fit.workout_service.dto.WorkoutProgressUpdateResponse;
import cs544.fit.workout_service.dto.WorkoutStatusUpdateRequestDTO;
import cs544.fit.workout_service.entity.UserWorkoutProgress;
import cs544.fit.workout_service.entity.WorkoutCategory;
import cs544.fit.workout_service.entity.WorkoutPlan;
import cs544.fit.workout_service.entity.WorkoutStatus;
import cs544.fit.workout_service.repository.UserWorkoutProgressRepository;
import cs544.fit.workout_service.repository.WorkoutCategoryRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserWorkoutProgressService {

    @Autowired
    private final WorkoutCategoryRepository categoryRepo;
    @Autowired
    private final UserWorkoutProgressRepository progressRepo;
    @Autowired
    private JwtUtil jwtUtil;

    private Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated or JWT missing");
        }

        // Get token from request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("JWT token missing in request");
        }
        String token = authHeader.substring(7);

        // Extract userId from JWT claims
        Claims claims = jwtUtil.extractAllClaims(token);
        Long userId = claims.get("userId", Long.class);
        if (userId == null) {
            throw new IllegalStateException("User ID not found in JWT claims");
        }
        return userId;
    }

    public List<UserWorkoutProgress> createProgressForCategories(List<Long> categoryIds) {
        Long userId = getLoggedInUserId();

        List<UserWorkoutProgress> newProgresses = new ArrayList<>();
        for (Long categoryId : categoryIds) {
            WorkoutCategory category = categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId));
            for (WorkoutPlan workout : category.getWorkoutPlans()) {
                // Check if progress already exists
                boolean exists = progressRepo.findByUserIdAndWorkoutId(userId, workout.getId()).isPresent();
                if (!exists) {
                    UserWorkoutProgress progress = new UserWorkoutProgress();
                    progress.setUserId(userId);
                    progress.setCategory(category);
                    progress.setWorkout(workout);
                    progress.setStatus(WorkoutStatus.NOT_STARTED);
                    newProgresses.add(progress);
                }
            }
        }
        return progressRepo.saveAll(newProgresses);
    }


    public List<UserWorkoutProgressDTO> getUserProgress() {
        Long userId = getLoggedInUserId();
        List<UserWorkoutProgress> progressList = progressRepo.findByUserId(userId);
        return progressList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    private UserWorkoutProgressDTO mapToDto(UserWorkoutProgress progress) {
        UserWorkoutProgressDTO dto = new UserWorkoutProgressDTO();
        dto.setUserId(progress.getUserId());
        dto.setWorkoutTitle(progress.getWorkout().getTitle());
        dto.setStatus(progress.getStatus().name());
        dto.setCategoryId(progress.getCategory().getId());
        dto.setCategoryName(progress.getCategory().getName());
        return dto;
    }

    public WorkoutProgressUpdateResponse updateProgressStatus(Long progressId, WorkoutStatusUpdateRequestDTO request) {

        UserWorkoutProgress progress = progressRepo.findById(progressId)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        WorkoutStatus status = request.getStatus();

        progress.setStatus(status);
        if (status == WorkoutStatus.IN_PROGRESS) {
            progress.setStartedAt(LocalDateTime.now());
        } else if (status == WorkoutStatus.FINISHED) {
            progress.setCompletedAt(LocalDateTime.now());
        }
        UserWorkoutProgress updated = progressRepo.save(progress);
        Long workoutId = updated.getWorkout() != null ? updated.getWorkout().getId() : null;
        Long categoryId = updated.getCategory() != null ? updated.getCategory().getId() : null;

        return new WorkoutProgressUpdateResponse(workoutId, categoryId, updated.getStatus());
    }
}

