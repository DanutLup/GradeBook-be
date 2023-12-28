package gradebook.service;

import gradebook.dto.*;
import gradebook.exceptions.UserException;
import gradebook.repository.db.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserLoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto){
        UserEntity userEntity = userRepository.findByEmail(userLoginRequestDto.getEmail())
                .orElseThrow(() -> new UserException("User Not found"));
        if(userEntity.getPassword().equals(userLoginRequestDto.getPassword())){
            log.info("Login successful");

            return UserLoginResponseDto.builder()
                    .email(userEntity.getEmail())
                    .role(UserRoleDto.valueOf(userEntity.getRole().name()))
                    .firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName())
                    .build();
        }
        else {
            log.error("Incorrect password");
            throw new UserException("Incorrect password");
        }
    }

    @Override
    public UserStatisticsResponseDto getUserStatistics(){
        Pageable pageable = PageRequest.of(0, 200);
        int numberOfStudents = userRepository.findByRole(UserRole.STUDENT, pageable).getNumberOfElements();
        int numberOfTeachers = userRepository.findByRole(UserRole.TEACHER, pageable).getNumberOfElements();

        return UserStatisticsResponseDto.builder()
                .numberOfStudents(numberOfStudents)
                .numberOfTeachers(numberOfTeachers)
                .build();
    }

    @Override
    @Transactional
    public UsersPageResponseDto getTeachers(UsersPageRequestDto usersPageRequestDto){
        Pageable pageable = PageRequest.of(usersPageRequestDto.getPageNumber(), usersPageRequestDto.getPageSize());
        if(usersPageRequestDto.getRole().equals(UserRoleDto.STUDENT)){
            Page<StudentEntity> students = studentRepository.findAll(pageable);
            StudentEntity student = StudentEntity.builder().email("test")
                    .id(50)
                    .role(UserRole.STUDENT)
                    .firstName("test")
                    .lastName("test")
                    .password("password")
                    .build();
            studentRepository.deleteById(105);
            Page<UserResponseDto> studentsResponse = students.map(UserServiceImpl::toUserResponseDto);
            return UsersPageResponseDto.builder()
                    .users(studentsResponse)
                    .build();
        }
        Page<UserEntity> teachers = userRepository.findByRole(UserRole.valueOf(usersPageRequestDto.getRole().name()), pageable);

        Page<UserResponseDto> teachersResponse = teachers.map(UserServiceImpl::toUserResponseDto);
        return UsersPageResponseDto.builder()
                .users(teachersResponse)
                .build();
    }

    private static UserResponseDto toUserResponseDto(UserEntity userEntity) {
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .lastName(userEntity.getLastName())
                .firstName(userEntity.getFirstName())
                .email(userEntity.getEmail())
                .build();
    }
}
