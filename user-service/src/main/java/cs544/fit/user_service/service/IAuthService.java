package cs544.fit.user_service.service;

import cs544.fit.user_service.dto.*;

import java.util.List;

public interface IAuthService {
    RegisterResponse registerUser(RegisterRequest registerRequest);
    RegisterResponse registerCoach(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    List<UserWithProfileResponse> getAllUsersWithProfiles();
}
