package com.fit.workout_progress_tracking.service;

import com.fit.workout_progress_tracking.dto.CoachProfileResponse;
import com.fit.workout_progress_tracking.dto.UserProfileResponse;
import com.fit.workout_progress_tracking.dto.WorkoutCategoryDTO;
import com.fit.workout_progress_tracking.dto.WorkoutPlanDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private String baseUrl = "http://localhost:8081/api/profile";
    private String workoutsUrl = "http://localhost:8082/api/workouts";
    private String categoriesUrl = "http://localhost:8082/api/categories";

    private final RestTemplate restTemplate;

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserProfileResponse getUserProfile(Long userId) {
        String url = baseUrl + "/user/" + userId;
        return restTemplate.getForObject(url, UserProfileResponse.class);
    }

    public CoachProfileResponse getCoachProfile(Long userId) {
        String url = baseUrl + "/coach/" + userId;
        return restTemplate.getForObject(url, CoachProfileResponse.class);
    }

    // -------------------- Workouts --------------------
    public WorkoutPlanDTO[] getAllWorkouts() {
        return restTemplate.getForObject(workoutsUrl, WorkoutPlanDTO[].class);
    }

    public WorkoutPlanDTO getWorkoutById(Long id) {
        String url = workoutsUrl + "/" + id;
        return restTemplate.getForObject(url, WorkoutPlanDTO.class);
    }

    public WorkoutPlanDTO[] getWorkoutsByCategory(Long categoryId) {
        String url = workoutsUrl + "/category/" + categoryId;
        return restTemplate.getForObject(url, WorkoutPlanDTO[].class);
    }

    public WorkoutPlanDTO[] getWorkoutsByUser(Long userId) {
        String url = workoutsUrl + "/user/" + userId;
        return restTemplate.getForObject(url, WorkoutPlanDTO[].class);
    }

    // -------------------- Categories --------------------
    public WorkoutCategoryDTO[] getAllCategories() {
        return restTemplate.getForObject(categoriesUrl, WorkoutCategoryDTO[].class);
    }

    public WorkoutCategoryDTO getCategoryById(Long id) {
        String url = categoriesUrl + "/" + id;
        return restTemplate.getForObject(url, WorkoutCategoryDTO.class);
    }
}