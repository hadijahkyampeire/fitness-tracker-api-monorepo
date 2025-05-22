package cs544.fit.user_service.service;

import cs544.fit.user_service.dto.*;
import cs544.fit.user_service.entity.*;
import cs544.fit.user_service.repository.*;
import cs544.fit.user_service.service.impl.AuthService;
import cs544.fit.user_service.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {

    @Mock private UserRepo userRepo;
    @Mock private CoachProfileRepo coachProfileRepo;
    @Mock private UserProfileRepo userProfileRepo;
    @Mock private RoleRepo roleRepo;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void registerUser_shouldCreateUserWithUserRole() {
        RegisterRequest request = new RegisterRequest( "testuser", "test@example.com","password");

        Role userRole = new Role(1L, "USER");

        // Stub method calls
        when(userRepo.existsByEmail("test@example.com")).thenReturn(false);
        when(roleRepo.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Capture saved user
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepo.save(userCaptor.capture())).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L); // simulate DB assigning an ID
            return u;
        });

        // Call the service
        RegisterResponse response = authService.registerUser(request);

        // Assert returned response
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertEquals("USER", response.getRole());

        // Assert saved user values
        User savedUser = userCaptor.getValue();
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(userRole, savedUser.getRole());

        // Verify interactions
        verify(userRepo).existsByEmail("test@example.com");
        verify(userRepo).save(any(User.class));
        verify(roleRepo).findByName("USER");
        verify(passwordEncoder).encode("password");
    }

    @Test
    void registerUser_shouldThrowIfEmailExists() {
        RegisterRequest request = new RegisterRequest("test@example.com", "testuser", "password");
        when(userRepo.existsByEmail("test@example.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> authService.registerUser(request));
    }

    @Test
    void login_shouldReturnTokensForValidCredentials() {
        LoginRequest request = new LoginRequest("test@example.com", "password");
        Role role = new Role(1L, "USER");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setRole(role);
        user.setPassword("encodedPassword");

        Authentication auth = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(any(), any(), any())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken(any(), any(), any())).thenReturn("refreshToken");

        LoginResponse response = authService.login(request);

        assertEquals("accessToken", response.getAccessToken());
        assertEquals("refreshToken", response.getRefreshToken());
        assertEquals("testuser", response.getUsername());
        assertEquals("USER", response.getRole());
    }


    @Test
    void login_shouldThrowForInvalidCredentials() {
        LoginRequest request = new LoginRequest("wrong@example.com", "wrongpass");
        doThrow(new RuntimeException("Bad credentials")).when(authenticationManager).authenticate(any());
        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void getAllUsersWithProfiles_shouldReturnMappedProfiles() {
        Role role = new Role(1L, "USER");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setRole(role);

        UserProfile userProfile = new UserProfile();
        userProfile.setId(10L);
        userProfile.setWeight(70.0);
        userProfile.setHeight(175.0);
        userProfile.setAge(25);

        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));
        when(userProfileRepo.findByUserId(1L)).thenReturn(Optional.of(userProfile));
        when(coachProfileRepo.findByUserId(1L)).thenReturn(Optional.empty());

        List<UserWithProfileResponse> result = authService.getAllUsersWithProfiles();

        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        assertNotNull(result.get(0).getUserProfile());
        assertNull(result.get(0).getCoachProfile());
    }
}


