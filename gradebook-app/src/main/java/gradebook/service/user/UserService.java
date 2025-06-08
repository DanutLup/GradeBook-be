package gradebook.service.user;

import gradebook.dto.request.user.*;
import gradebook.dto.response.user.UserResponseDto;
import gradebook.dto.response.user.UserStatisticsResponseDto;
import gradebook.dto.response.user.UsersPageResponseDto;

public interface UserService {
  //  UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);

  UserStatisticsResponseDto getUserStatistics();

  UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto);

  void deleteUser(UserDeleteRequestDto userDeleteRequestDto);

  void createUser(UserCreateRequestDto userCreateRequestDto);

  UserResponseDto getUser(int userId);

  void updateUser(UserUpdateRequestDto userUpdateRequestDto);

  void createEnrollment(EnrollmentRequestDto enrollmentRequestDto);

  void deleteEnrollment(int studentId, int courseId);

  void editGrade(EnrollmentRequestDto enrollmentRequestDto);
}
