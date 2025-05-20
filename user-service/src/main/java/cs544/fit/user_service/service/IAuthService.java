package cs544.fit.user_service.service;

import cs544.fit.user_service.dto.LoginRequest;
import cs544.fit.user_service.dto.LoginResponse;
import cs544.fit.user_service.dto.RegisterRequest;
import cs544.fit.user_service.dto.RegisterResponse;

public interface IAuthService {
    RegisterResponse registerUser(RegisterRequest registerRequest);
    RegisterResponse registerCoach(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}
