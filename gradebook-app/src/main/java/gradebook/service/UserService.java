package gradebook.service;

import gradebook.dto.*;

public interface UserService {
    UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);
    UserStatisticsResponseDto getUserStatistics();
    UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto);
    void createUser(UserCreateRequestDto userCreateRequestDto);
}
