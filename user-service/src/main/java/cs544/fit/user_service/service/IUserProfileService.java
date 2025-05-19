package cs544.fit.user_service.service;

import cs544.fit.user_service.dto.UserProfileRequest;
import cs544.fit.user_service.dto.UserProfileResponse;

public interface IUserProfileService {
    UserProfileResponse createProfile(Long userId, UserProfileRequest request);
    UserProfileResponse getProfile(Long userId);
    UserProfileResponse updateProfile(Long userId, UserProfileRequest request);
}
