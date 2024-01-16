package gradebook.api;

import gradebook.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/delete-user")
    @ResponseStatus(HttpStatus.OK)
    void deleteUser(@RequestBody UserDeleteRequestDto userDeleteRequestDto);

    @GetMapping("/get-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserResponseDto getUser(@PathVariable int userId);

    @PutMapping("/update-user")
    @ResponseStatus(HttpStatus.OK)
    void updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto);

    @PostMapping("/create-enrollment")
    @ResponseStatus(HttpStatus.OK)
    void createEnrollment(@RequestBody EnrollmentRequestDto enrollmentRequestDto);

    @DeleteMapping("/delete-enrollment/{studentId}/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteEnrollment(@PathVariable int studentId, @PathVariable int courseId);

    @PutMapping("/edit-grade")
    @ResponseStatus(HttpStatus.OK)
    void editGrade(@RequestBody EnrollmentRequestDto enrollmentRequestDto);
}
