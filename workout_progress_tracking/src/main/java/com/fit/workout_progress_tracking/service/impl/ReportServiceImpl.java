package com.fit.workout_progress_tracking.service.impl;

import com.fit.workout_progress_tracking.config.JwtUtil;
import com.fit.workout_progress_tracking.dto.*;
import com.fit.workout_progress_tracking.entity.WorkoutStatus;
import com.fit.workout_progress_tracking.service.UserClient;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl {
    @Autowired
    private UserClient userClient;
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

    public ProgressSummaryDTO getProgressReportForCurrentUser() {
        // 1. Fetch progress
        UserWorkoutProgressDTO[] progressArray = userClient.getUserWorkoutProgress();
        List<UserWorkoutProgressDTO> progressList = Arrays.asList(progressArray);

        if (progressList.isEmpty()) return null;

        Long userId = getLoggedInUserId();

        // 2. Fetch user info
        UserProfileResponse userProfile = userClient.getUserProfile(userId);

        // 3. Group by category
        Map<Long, List<UserWorkoutProgressDTO>> groupedByCategory = progressList.stream()
                .collect(Collectors.groupingBy(UserWorkoutProgressDTO::getCategoryId));

        List<CategoryProgressDTO> categoryProgressList = new ArrayList<>();

        int totalWorkouts = 0;
        int completed = 0;
        int notStarted = 0;
        int inProgress = 0;

        for (Map.Entry<Long, List<UserWorkoutProgressDTO>> entry : groupedByCategory.entrySet()) {
            Long categoryId = entry.getKey();
            List<UserWorkoutProgressDTO> categoryItems = entry.getValue();

            int total = categoryItems.size();
            int notStartedCount = (int) categoryItems.stream()
                    .filter(p -> WorkoutStatus.valueOf(p.getStatus()) == WorkoutStatus.NOT_STARTED)
                    .count();

            int inProgressCount = (int) categoryItems.stream()
                    .filter(p -> WorkoutStatus.valueOf(p.getStatus()) == WorkoutStatus.IN_PROGRESS)
                    .count();

            int completedCount = (int) categoryItems.stream()
                    .filter(p -> WorkoutStatus.valueOf(p.getStatus()) == WorkoutStatus.FINISHED)
                    .count();

            CategoryProgressDTO cp = new CategoryProgressDTO();
            cp.setCategoryId(categoryId);
            cp.setCategoryName(categoryItems.get(0).getCategoryName());
            cp.setTotalWorkouts(total);
            cp.setNotStarted(notStartedCount);
            cp.setInProgress(inProgressCount);
            cp.setCompleted(completedCount);
            cp.setWorkoutTitles(categoryItems.stream()
                    .map(UserWorkoutProgressDTO::getWorkoutTitle)
                    .collect(Collectors.toList()));

            categoryProgressList.add(cp);

            totalWorkouts += total;
            completed += completedCount;
            notStarted += notStartedCount;
            inProgress += inProgressCount;
        }

        // 4. Final report
        ProgressSummaryDTO report = new ProgressSummaryDTO();
        report.setUserId(userId);
        report.setFullName(userProfile.getUsername());
        report.setEmail(userProfile.getUserEmail());
        report.setAge(userProfile.getAge());
        report.setWeight(userProfile.getWeight());
        report.setHeight(userProfile.getHeight());
        report.setBmi(userProfile.getBmi());
        report.setGender(userProfile.getGender().toString());
        report.setRole(userProfile.getRole());
        report.setMedicalConditions(userProfile.getMedicalConditions());

        report.setTotalWorkouts(totalWorkouts);
        report.setCompleted(completed);
        report.setInProgress(inProgress);
        report.setNotStarted(notStarted);

        report.setCategoryProgress(categoryProgressList);

        return report;
    }


}
