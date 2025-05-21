package com.fit.workout_progress_tracking.service.impl;

import com.fit.workout_progress_tracking.config.JwtUtil;
import com.fit.workout_progress_tracking.dto.UserInfoDTO;
import com.fit.workout_progress_tracking.dto.UserProfileResponse;
import com.fit.workout_progress_tracking.dto.WorkoutCategoryDTO;
import com.fit.workout_progress_tracking.service.UserClient;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

    public UserInfoDTO getUserInfo() {
        Long userId = getLoggedInUserId();
        System.out.println("Read This User Id: " + userId);

        UserProfileResponse profile = userClient.getUserProfile(userId);
        System.out.println("Username: " + profile.getUsername());
//        System.out.println("Read This User Profile: " + profile);

        // a profile has a list of goal category IDs
        List<String> goals;

        // If profile has goal IDs that map to category names
        if (profile.getGoalIds() != null) {
            goals = profile.getGoalIds().stream()
                    .map(id -> {
                        WorkoutCategoryDTO category = userClient.getCategoryById(id);
                        return category != null ? category.name() : null;
                    })
                    .filter(name -> name != null)
                    .collect(Collectors.toList());
        } else {
            goals = List.of(); // default empty if not present
        }

        UserInfoDTO dto = new UserInfoDTO();
        dto.setUserId(profile.getUserId());
        dto.setUserName(profile.getUsername());
        dto.setUserEmail(profile.getUserEmail());
        dto.setWeight(profile.getWeight());
        dto.setHeight(profile.getHeight());
        dto.setGoals(goals);

        return dto;
    }
}
