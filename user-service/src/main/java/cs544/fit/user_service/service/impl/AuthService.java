package cs544.fit.user_service.service.impl;

import cs544.fit.user_service.dto.*;
import cs544.fit.user_service.entity.CoachProfile;
import cs544.fit.user_service.entity.Role;
import cs544.fit.user_service.entity.User;
import cs544.fit.user_service.entity.UserProfile;
import cs544.fit.user_service.repository.CoachProfileRepo;
import cs544.fit.user_service.repository.RoleRepo;
import cs544.fit.user_service.repository.UserProfileRepo;
import cs544.fit.user_service.repository.UserRepo;
import cs544.fit.user_service.service.IAuthService;
import cs544.fit.user_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CoachProfileRepo coachProfileRepo;

    @Autowired
    private UserProfileRepo userProfileRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        return registerWithRole(registerRequest, "USER");
    }

    @Override
    public RegisterResponse registerCoach(RegisterRequest registerRequest) {
        return registerWithRole(registerRequest, "COACH");
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (Exception e) {
            // Print to console or logger
            e.printStackTrace();
            throw new RuntimeException("Invalid credentials");
        }

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));

        String accessToken = jwtUtil.generateToken(user.getEmail(),user.getId(), user.getRole().getName());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(),user.getId(), user.getRole().getName());

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserWithProfileResponse> getAllUsersWithProfiles() {
        System.out.println("getAllUsersWithProfiles");
        List<User> users = userRepo.findAll();
        return users.stream().map(this::mapToUserWithProfileResponse).collect(Collectors.toList());
    }

    private UserWithProfileResponse mapToUserWithProfileResponse(User user) {
        UserProfileResponse userProfileResponse = null;
        CoachProfileResponse coachProfileResponse = null;

        // Check for UserProfile
        Optional<UserProfile> userProfile = userProfileRepo.findByUserId(user.getId());
        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            userProfileResponse = new UserProfileResponse(
                    profile.getId(),
                    profile.getWeight(),
                    profile.getHeight(),
                    profile.getAge(),
                    profile.getGender(), // Convert enum to String
                    profile.getMedicalConditions(),
                    profile.getGoalIds(),
                    user.getId(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getRole().getName(),
                    profile.getCreatedAt(),
                    profile.getLastUpdated()
            );
        }

        // Check for CoachProfile
        Optional<CoachProfile> coachProfile = coachProfileRepo.findByUserId(user.getId());
        if (coachProfile.isPresent()) {
            CoachProfile profile = coachProfile.get();
            coachProfileResponse = new CoachProfileResponse(
                    profile.getId(),
                    profile.getAge(),
                    profile.getGender(), // Convert enum to String
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

        return new UserWithProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().getName(),
                userProfileResponse,
                coachProfileResponse
        );
    }

    private RegisterResponse registerWithRole(RegisterRequest registerRequest, String roleName) {
        if(userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Role role = roleRepo.findByName(roleName).orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        User savedUser = userRepo.save(user);
        return new RegisterResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), role.getName());
    }
}
