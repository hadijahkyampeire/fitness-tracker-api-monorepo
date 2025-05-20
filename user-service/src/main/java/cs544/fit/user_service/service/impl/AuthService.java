package cs544.fit.user_service.service.impl;

import cs544.fit.user_service.dto.LoginRequest;
import cs544.fit.user_service.dto.LoginResponse;
import cs544.fit.user_service.dto.RegisterRequest;
import cs544.fit.user_service.dto.RegisterResponse;
import cs544.fit.user_service.entity.Role;
import cs544.fit.user_service.entity.User;
import cs544.fit.user_service.repository.RoleRepo;
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

@Service
@Transactional
public class AuthService implements IAuthService {
    @Autowired
    private UserRepo userRepo;

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
