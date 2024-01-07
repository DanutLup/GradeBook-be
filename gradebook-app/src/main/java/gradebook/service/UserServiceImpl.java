package gradebook.service;

import gradebook.dto.*;
import gradebook.exceptions.UserException;
import gradebook.repository.db.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public void createUser(UserCreateRequestDto userCreateRequestDto){
        verifyEmailExists(userCreateRequestDto);
        verifyCnpExists(userCreateRequestDto);

        if(userCreateRequestDto.getRole().equals(UserRoleDto.STUDENT)){
            saveStudent(userCreateRequestDto);
        }
        else if(userCreateRequestDto.getRole().equals(UserRoleDto.TEACHER)){
            saveTeacher(userCreateRequestDto);
        }
    }

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
    public UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto){
        Pageable pageable = PageRequest.of(usersPageRequestDto.getPageNumber(), usersPageRequestDto.getPageSize());

        if(usersPageRequestDto.getRole().equals(UserRoleDto.STUDENT)){
            return getStudentsPageResponseDto(pageable);
        }
        else if(usersPageRequestDto.getRole().equals(UserRoleDto.TEACHER)){
            return getTeacherPageResponseDto(pageable);
        }
        return getUsersPageResponseDto(pageable);
    }

    private UsersPageResponseDto getUsersPageResponseDto(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        Page<UserResponseDto> usersResponse = users.map(UserServiceImpl::toUserResponseDto);
        return UsersPageResponseDto.builder()
                .users(usersResponse)
                .build();
    }

    private UsersPageResponseDto getTeacherPageResponseDto(Pageable pageable) {
        Page<TeacherEntity> teachers = teacherRepository.findAll(pageable);

        Page<UserResponseDto> teachersResponse = teachers.map(UserServiceImpl::toUserResponseDto);
        return UsersPageResponseDto.builder()
                .users(teachersResponse)
                .build();
    }

    private UsersPageResponseDto getStudentsPageResponseDto(Pageable pageable) {
        Page<StudentEntity> students = studentRepository.findAll(pageable);
        Page<UserResponseDto> studentsResponse = students.map(UserServiceImpl::toUserResponseDto);
        return UsersPageResponseDto.builder()
                .users(studentsResponse)
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

    private void verifyCnpExists(UserCreateRequestDto userCreateRequestDto) {
        Optional<UserEntity> userEntity = userRepository.findByCnp(userCreateRequestDto.getCnp());
        if(userEntity.isPresent()){
            throw new UserException(String.format("User with CNP: %s already exists", userCreateRequestDto.getCnp()));
        }
    }

    private void verifyEmailExists(UserCreateRequestDto userCreateRequestDto) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(userCreateRequestDto.getEmail());
        if(userEntity.isPresent()){
            throw new UserException(String.format("User with email %s already exists!", userCreateRequestDto.getEmail()));
        }
    }

    private void saveTeacher(UserCreateRequestDto userCreateRequestDto) {
        TeacherEntity teacherEntity = TeacherEntity.builder()
                .firstName(userCreateRequestDto.getFirstName())
                .lastName(userCreateRequestDto.getLastName())
                .email(userCreateRequestDto.getEmail())
                .password(userCreateRequestDto.getCnp())
                .role(UserRole.TEACHER)
                .cnp(userCreateRequestDto.getCnp())
                .build();

        teacherRepository.save(teacherEntity);
    }

    private void saveStudent(UserCreateRequestDto userCreateRequestDto) {
        StudentEntity studentEntity =
                StudentEntity.builder()
                        .firstName(userCreateRequestDto.getFirstName())
                        .lastName(userCreateRequestDto.getLastName())
                        .email(userCreateRequestDto.getEmail())
                        .password(userCreateRequestDto.getCnp())
                        .role(UserRole.STUDENT)
                        .cnp(userCreateRequestDto.getCnp())
                        .build();

        studentRepository.save(studentEntity);
    }
}
