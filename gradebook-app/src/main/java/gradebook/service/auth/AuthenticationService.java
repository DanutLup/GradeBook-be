package gradebook.service.auth;

import gradebook.dto.request.user.UserLoginRequestDto;
import gradebook.dto.response.user.UserLoginResponseDto;

public interface AuthenticationService {
  UserLoginResponseDto authenticate(UserLoginRequestDto request);
}
