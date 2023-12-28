package gradebook.service;

import gradebook.dto.*;

public interface UserService {
    UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);
    UserStatisticsResponseDto getUserStatistics();

    UsersPageResponseDto getTeachers(UsersPageRequestDto usersPageRequestDto);
}
