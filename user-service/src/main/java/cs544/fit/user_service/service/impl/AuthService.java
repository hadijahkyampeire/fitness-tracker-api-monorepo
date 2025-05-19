package cs544.fit.user_service.service.impl;

import cs544.fit.user_service.dto.RegisterRequest;
import cs544.fit.user_service.dto.RegisterResponse;
import cs544.fit.user_service.entity.Role;
import cs544.fit.user_service.entity.User;
import cs544.fit.user_service.repository.RoleRepo;
import cs544.fit.user_service.repository.UserRepo;
import cs544.fit.user_service.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        return registerWithRole(registerRequest, "USER");
    }

    @Override
    public RegisterResponse registerCoach(RegisterRequest registerRequest) {
        return registerWithRole(registerRequest, "COACH");
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
