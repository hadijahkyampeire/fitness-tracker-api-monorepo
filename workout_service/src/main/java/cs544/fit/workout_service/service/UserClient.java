package cs544.fit.workout_service.service;

import cs544.fit.workout_service.dto.CoachProfileResponse;
import cs544.fit.workout_service.dto.UserProfileResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private String baseUrl = "http://localhost:8081/api/profile";

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
}

