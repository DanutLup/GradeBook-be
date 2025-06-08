package gradebook.rest;

import gradebook.api.UserApi;
import gradebook.dto.request.user.*;
import gradebook.dto.response.user.UserLoginResponseDto;
import gradebook.dto.response.user.UserResponseDto;
import gradebook.dto.response.user.UserStatisticsResponseDto;
import gradebook.dto.response.user.UsersPageResponseDto;
import gradebook.service.auth.AuthenticationService;
import gradebook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {
  private final UserService userService;
  private final AuthenticationService authenticationService;

  @Override
  public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
    log.info("Received request for login user {}", userLoginRequestDto.getEmail());
    return authenticationService.authenticate(userLoginRequestDto);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public UserStatisticsResponseDto getUserStatistics() {
    return userService.getUserStatistics();
  }

  @Override
  public UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto) {
    return userService.getUsers(usersPageRequestDto);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void createUser(UserCreateRequestDto userCreateRequestDto) {
    userService.createUser(userCreateRequestDto);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void deleteUser(UserDeleteRequestDto userDeleteRequestDto) {
    userService.deleteUser(userDeleteRequestDto);
  }

  @Override
  public UserResponseDto getUser(int userId) {
    return userService.getUser(userId);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void updateUser(UserUpdateRequestDto userUpdateRequestDto) {
    log.info("Received request to update user {}", userUpdateRequestDto);
    userService.updateUser(userUpdateRequestDto);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void createEnrollment(EnrollmentRequestDto enrollmentRequestDto) {
    userService.createEnrollment(enrollmentRequestDto);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void deleteEnrollment(int studentId, int courseId) {
    userService.deleteEnrollment(studentId, courseId);
  }

  @Override
  @PreAuthorize("hasAuthority('ROLE_TEACHER')")
  public void editGrade(EnrollmentRequestDto enrollmentRequestDto) {
    userService.editGrade(enrollmentRequestDto);
  }
}
