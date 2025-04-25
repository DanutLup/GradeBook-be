package gradebook.rest;

import gradebook.api.UserApi;
import gradebook.dto.request.user.*;
import gradebook.dto.response.user.UserLoginResponseDto;
import gradebook.dto.response.user.UserResponseDto;
import gradebook.dto.response.user.UserStatisticsResponseDto;
import gradebook.dto.response.user.UsersPageResponseDto;
import gradebook.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
  private final UserService userService;

  @Override
  public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto) {
    return userService.loginUser(userLoginRequestDto);
  }

  @Override
  public UserStatisticsResponseDto getUserStatistics() {
    return userService.getUserStatistics();
  }

  @Override
  public UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto) {
    return userService.getUsers(usersPageRequestDto);
  }

  @Override
  public void createUser(UserCreateRequestDto userCreateRequestDto) {
    userService.createUser(userCreateRequestDto);
  }

  @Override
  public void deleteUser(UserDeleteRequestDto userDeleteRequestDto) {
    userService.deleteUser(userDeleteRequestDto);
  }

  @Override
  public UserResponseDto getUser(int userId) {
    return userService.getUser(userId);
  }

  @Override
  public void updateUser(UserUpdateRequestDto userUpdateRequestDto) {
    userService.updateUser(userUpdateRequestDto);
  }

  @Override
  public void createEnrollment(EnrollmentRequestDto enrollmentRequestDto) {
    userService.createEnrollment(enrollmentRequestDto);
  }

  @Override
  public void deleteEnrollment(int studentId, int courseId) {
    userService.deleteEnrollment(studentId, courseId);
  }

  @Override
  public void editGrade(EnrollmentRequestDto enrollmentRequestDto) {
    userService.editGrade(enrollmentRequestDto);
  }
}
