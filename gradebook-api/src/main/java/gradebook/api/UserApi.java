package gradebook.api;

import gradebook.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface UserApi {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    UserLoginResponseDto loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto);

    @GetMapping("/get-user-statistics")
    @ResponseStatus(HttpStatus.OK)
    UserStatisticsResponseDto getUserStatistics();

    @PostMapping("/get-users")
    @ResponseStatus(HttpStatus.OK)
    UsersPageResponseDto getUsers(@RequestBody UsersPageRequestDto usersPageRequestDto);

    @PostMapping("/create-user")
    @ResponseStatus(HttpStatus.OK)
    void createUser(@RequestBody UserCreateRequestDto userCreateRequestDto);
}
