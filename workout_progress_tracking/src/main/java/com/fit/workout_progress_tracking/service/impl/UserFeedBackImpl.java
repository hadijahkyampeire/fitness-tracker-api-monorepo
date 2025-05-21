package com.fit.workout_progress_tracking.service.impl;

import com.fit.workout_progress_tracking.config.JwtUtil;
import com.fit.workout_progress_tracking.dto.UserRequestFeedBackDTO;
import com.fit.workout_progress_tracking.entity.UserFeedBack;
import com.fit.workout_progress_tracking.repositories.UserFeedBackRepository;
import com.fit.workout_progress_tracking.service.UserFeedBackService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
@Transactional
public class UserFeedBackImpl implements UserFeedBackService {
    @Autowired
    private UserFeedBackRepository ufeedRepo;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public List<UserFeedBack> findByUserId() {
        Long userIdLong = getLoggedInUserId();
        System.out.println("-----------------------" + userIdLong + "------------------------");
        return ufeedRepo.findByUserId(userIdLong);
    }

    public UserFeedBack addFeedBack(UserRequestFeedBackDTO userRequestFeedBackDTO) {
        Long userId = getLoggedInUserId();
        UserFeedBack userFeedBack = new UserFeedBack();
        userFeedBack.setUserId(userId);
        userFeedBack.setNewWeight(userRequestFeedBackDTO.getNewWeight());
        userFeedBack.setComment(userRequestFeedBackDTO.getComment());
        ufeedRepo.save(userFeedBack);
        return userFeedBack;
    }

    public UserFeedBack updateFeedback(Long feedbackId, UserRequestFeedBackDTO updatedDTO) {
        Long currentUserId = getLoggedInUserId();

        UserFeedBack feedback = ufeedRepo.findById(feedbackId)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found"));

        if (!feedback.getUserId().equals(currentUserId)) {
            throw new SecurityException("You are not authorized to edit this feedback");
        }

        feedback.setNewWeight(updatedDTO.getNewWeight());
        feedback.setComment(updatedDTO.getComment());

        return ufeedRepo.save(feedback);
    }


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
}
