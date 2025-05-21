package cs544.fit.user_service.service.impl;

import cs544.fit.user_service.dto.UserProfileRequest;
import cs544.fit.user_service.dto.UserProfileResponse;
import cs544.fit.user_service.entity.User;
import cs544.fit.user_service.entity.UserProfile;
import cs544.fit.user_service.repository.UserProfileRepo;
import cs544.fit.user_service.repository.UserRepo;
import cs544.fit.user_service.service.IUserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class UserProfileService implements IUserProfileService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Override
    public UserProfileResponse createProfile(Long userId, UserProfileRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (userProfileRepo.existsByUserId(userId)) {
            throw new RuntimeException("Profile already exists for this user");
        }

        UserProfile profile = new UserProfile();
        profile.setUser(user);
        profile.setWeight(request.getWeight());
        profile.setHeight(request.getHeight());
        profile.setAge(request.getAge());
        profile.setGender(request.getGender());
        profile.setMedicalConditions(request.getMedicalConditions());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setLastUpdated(LocalDateTime.now());

        UserProfile saved = userProfileRepo.save(profile);
        return mapToResponse(saved, user);
    }

    @Override
    public UserProfileResponse getProfile(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfile profile = userProfileRepo.findByUserId(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponse(profile, user);
    }

    @Override
    public UserProfileResponse updateProfile(Long userId, UserProfileRequest request) {
        UserProfile profile = userProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found"));

        profile.setWeight(request.getWeight());
        profile.setHeight(request.getHeight());
        profile.setAge(request.getAge());
        profile.setGender(request.getGender());
        profile.setMedicalConditions(request.getMedicalConditions());
        profile.setLastUpdated(LocalDateTime.now());

        UserProfile updated = userProfileRepo.save(profile);
        User user = profile.getUser();

        return new UserProfileResponse(
                updated.getId(),
                updated.getWeight(),
                updated.getHeight(),
                updated.getAge(),
                updated.getGender(),
                updated.getMedicalConditions(),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                updated.getCreatedAt(),
                updated.getLastUpdated()
        );
    }

    private UserProfileResponse mapToResponse(UserProfile profile, User user) {
        return new UserProfileResponse(
                profile.getId(),
                profile.getWeight(),
                profile.getHeight(),
                profile.getAge(),
                profile.getGender(),
                profile.getMedicalConditions(),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                profile.getCreatedAt(),
                profile.getLastUpdated()
        );
    }
}
