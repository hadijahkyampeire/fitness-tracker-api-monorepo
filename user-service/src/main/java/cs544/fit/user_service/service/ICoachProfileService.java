package cs544.fit.user_service.service;

import cs544.fit.user_service.dto.CoachProfileRequest;
import cs544.fit.user_service.dto.CoachProfileResponse;

public interface ICoachProfileService {
    CoachProfileResponse createProfile(Long userId, CoachProfileRequest request);
    CoachProfileResponse getProfile(Long userId);
    CoachProfileResponse updateProfile(Long userId, CoachProfileRequest request);
}
