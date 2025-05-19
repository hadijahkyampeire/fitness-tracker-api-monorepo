package cs544.fit.user_service.service.impl;

import cs544.fit.user_service.dto.CoachProfileRequest;
import cs544.fit.user_service.dto.CoachProfileResponse;
import cs544.fit.user_service.entity.CoachProfile;
import cs544.fit.user_service.entity.User;
import cs544.fit.user_service.repository.CoachProfileRepo;
import cs544.fit.user_service.repository.UserProfileRepo;
import cs544.fit.user_service.repository.UserRepo;
import cs544.fit.user_service.service.ICoachProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CoachProfileService implements ICoachProfileService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CoachProfileRepo coachProfileRepo;

    @Override
    public CoachProfileResponse createProfile(Long userId, CoachProfileRequest request) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (coachProfileRepo.existsByUserId(userId)) {
            throw new RuntimeException("Profile already exists for this coach");
        }

        CoachProfile profile = new CoachProfile();
        profile.setUser(user);
        profile.setGender(request.getGender());
        profile.setAge(request.getAge());
        profile.setQualification(request.getQualification());
        profile.setBio(request.getBio());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setLastUpdated(LocalDateTime.now());

        CoachProfile saved = coachProfileRepo.save(profile);

        return new CoachProfileResponse(
                saved.getId(),
                saved.getAge(),
                saved.getGender(),
                saved.getQualification(),
                saved.getBio(),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                saved.getCreatedAt(),
                saved.getLastUpdated()
        );
    }

    @Override
    public CoachProfileResponse getProfile(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CoachProfile profile = coachProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Coach profile not found"));

        return new CoachProfileResponse(
                profile.getId(),
                profile.getAge(),
                profile.getGender(),
                profile.getQualification(),
                profile.getBio(),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                profile.getCreatedAt(),
                profile.getLastUpdated()
        );
    }

    @Override
    public CoachProfileResponse updateProfile(Long userId, CoachProfileRequest request) {
        CoachProfile profile = coachProfileRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Coach profile not found"));

        profile.setGender(request.getGender());
        profile.setAge(request.getAge());
        profile.setQualification(request.getQualification());
        profile.setBio(request.getBio());
        profile.setLastUpdated(LocalDateTime.now());

        CoachProfile updated = coachProfileRepo.save(profile);

        User user = profile.getUser();

        return new CoachProfileResponse(
                updated.getId(),
                updated.getAge(),
                updated.getGender(),
                updated.getQualification(),
                updated.getBio(),
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getRole().getName(),
                updated.getCreatedAt(),
                updated.getLastUpdated()
        );
    }
}
