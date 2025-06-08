package gradebook.service.auth;

import gradebook.dto.request.user.UserLoginRequestDto;
import gradebook.dto.response.user.UserLoginResponseDto;
import gradebook.repository.db.UserRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    var user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Map<String, Object> extraClaims =
        Map.of(
            "userId",
            user.getId(),
            "role",
            user.getRole(),
            "firstName",
            user.getFirstName(),
            "lastName",
            user.getLastName());

    String jwtToken = jwtService.generateToken(extraClaims, user.getEmail());
    return new UserLoginResponseDto(jwtToken);
  }
}
