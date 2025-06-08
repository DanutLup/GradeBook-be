package gradebook.service.user;

import gradebook.dto.request.user.*;
import gradebook.dto.response.user.UserResponseDto;
import gradebook.dto.response.user.UserStatisticsResponseDto;
import gradebook.dto.response.user.UsersPageResponseDto;
import gradebook.dto.user.*;
import gradebook.exception.UserException;
import gradebook.repository.db.*;
import gradebook.repository.db.data.*;
import io.micrometer.common.util.StringUtils;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
  private final EnrollmentRepository enrollmentRepository;
  private final CourseRepository courseRepository;
  private final TeacherRepository teacherRepository;
  private final UserRepository userRepository;
  private final StudentRepository studentRepository;

  @Override
  public void deleteUser(UserDeleteRequestDto userDeleteRequestDto) {
    if (userDeleteRequestDto.getRole().equals(UserRoleDto.TEACHER)) {
      TeacherEntity teacherEntity =
          teacherRepository.findById(userDeleteRequestDto.getUserId()).orElseThrow();
      if (!teacherEntity.getCourses().isEmpty()) {
        throw new UserException("Please assign another teacher to the courses");
      }
      teacherRepository.deleteById(userDeleteRequestDto.getUserId());
    } else if (userDeleteRequestDto.getRole().equals(UserRoleDto.STUDENT)) {
      studentRepository.deleteById(userDeleteRequestDto.getUserId());
    }
  }

  @Override
  @Transactional
  public void createUser(UserCreateRequestDto userCreateRequestDto) {
    verifyEmailExists(userCreateRequestDto);
    verifyCnpExists(userCreateRequestDto);

    if (userCreateRequestDto.getRole().equals(UserRoleDto.STUDENT)) {
      saveStudent(userCreateRequestDto);
    } else if (userCreateRequestDto.getRole().equals(UserRoleDto.TEACHER)) {
      saveTeacher(userCreateRequestDto);
    }
  }

  @Override
  public UserResponseDto getUser(int userId) {
    UserEntity userEntity = userRepository.findById(userId).orElseThrow();
    return toUserResponseDto(userEntity);
  }

  @Override
  @Transactional
  public void updateUser(UserUpdateRequestDto userUpdateRequestDto) {
    UserEntity userEntity = userRepository.findById(userUpdateRequestDto.getId()).orElseThrow();

    userEntity.setEmail(userUpdateRequestDto.getEmail());
    userEntity.setFirstName(userUpdateRequestDto.getFirstName());
    userEntity.setLastName(userUpdateRequestDto.getLastName());
    userRepository.save(userEntity);
  }

  @Override
  @Transactional
  public void createEnrollment(EnrollmentRequestDto enrollmentRequestDto) {
    CourseEntity courseEntity =
        courseRepository
            .findById(enrollmentRequestDto.getCourseId())
            .orElseThrow(() -> new UserException("Course not found"));
    StudentEntity studentEntity =
        studentRepository
            .findById(enrollmentRequestDto.getStudentId())
            .orElseThrow(() -> new UserException("Student not found"));
    if (enrollmentRepository
        .findByStudentIdAndCourseId(studentEntity.getId(), courseEntity.getId())
        .isPresent()) {
      throw new UserException("The student is already enrolled to this course");
    }

    EnrollmentEntity enrollmentEntity =
        EnrollmentEntity.builder()
            .date(LocalDate.now())
            .course(courseEntity)
            .student(studentEntity)
            .build();

    enrollmentRepository.saveAndFlush(enrollmentEntity);
  }

  @Override
  @Transactional
  public void deleteEnrollment(int studentId, int courseId) {
    EnrollmentEntity enrollmentEntity =
        enrollmentRepository
            .findByStudentIdAndCourseId(studentId, courseId)
            .orElseThrow(() -> new UserException("Enrollment not found"));

    enrollmentRepository.delete(enrollmentEntity);
  }

  @Override
  public void editGrade(EnrollmentRequestDto enrollmentRequestDto) {
    EnrollmentEntity enrollmentEntity =
        enrollmentRepository
            .findByStudentIdAndCourseId(
                enrollmentRequestDto.getStudentId(), enrollmentRequestDto.getCourseId())
            .orElseThrow(() -> new UserException("Enrollment not found"));
    if (enrollmentRequestDto.getGrade() < 1 || enrollmentRequestDto.getGrade() > 10) {
      throw new UserException("Please enter a value between 1 and 10");
    }
    enrollmentEntity.setGrade(enrollmentRequestDto.getGrade());
    enrollmentRepository.save(enrollmentEntity);
  }

  @Override
  public UserStatisticsResponseDto getUserStatistics() {
    Pageable pageable = PageRequest.of(0, 200);
    int numberOfStudents =
        userRepository.findByRole(UserRole.STUDENT, pageable).getNumberOfElements();
    int numberOfTeachers =
        userRepository.findByRole(UserRole.TEACHER, pageable).getNumberOfElements();
    int numberOfCourses = courseRepository.findAll().size();

    return UserStatisticsResponseDto.builder()
        .numberOfStudents(numberOfStudents)
        .numberOfTeachers(numberOfTeachers)
        .numberOfCourses(numberOfCourses)
        .build();
  }

  @Override
  @Transactional
  public UsersPageResponseDto getUsers(UsersPageRequestDto usersPageRequestDto) {
    Pageable pageable =
        PageRequest.of(usersPageRequestDto.getPageNumber(), usersPageRequestDto.getPageSize());

    if (usersPageRequestDto.getRole().equals(UserRoleDto.STUDENT)) {
      return getStudentsPageResponseDto(usersPageRequestDto.getUserName(), pageable);
    } else if (usersPageRequestDto.getRole().equals(UserRoleDto.TEACHER)) {
      return getTeacherPageResponseDto(usersPageRequestDto.getUserName(), pageable);
    }
    return getUsersPageResponseDto(pageable);
  }

  private UsersPageResponseDto getUsersPageResponseDto(Pageable pageable) {
    Page<UserEntity> users = userRepository.findAll(pageable);
    Page<UserResponseDto> usersResponse = users.map(UserServiceImpl::toUserResponseDto);
    return UsersPageResponseDto.builder().users(usersResponse).build();
  }

  private UsersPageResponseDto getTeacherPageResponseDto(String userName, Pageable pageable) {
    Page<TeacherEntity> teachers;
    if (!StringUtils.isBlank(userName)) {
      Specification<TeacherEntity> studentSpecification =
          UserCriteriaBuilder.nameMatchesPrefixForTeacher(userName);
      teachers = teacherRepository.findAll(studentSpecification, pageable);
    } else {
      teachers = teacherRepository.findAll(pageable);
    }
    Page<UserResponseDto> teachersResponse = teachers.map(UserServiceImpl::toUserResponseDto);
    return UsersPageResponseDto.builder().users(teachersResponse).build();
  }

  private UsersPageResponseDto getStudentsPageResponseDto(String userName, Pageable pageable) {
    Page<StudentEntity> students;
    if (!StringUtils.isBlank(userName)) {
      Specification<StudentEntity> studentSpecification =
          UserCriteriaBuilder.nameMatchesPrefixForStudent(userName);
      students = studentRepository.findAll(studentSpecification, pageable);
    } else {
      students = studentRepository.findAll(pageable);
    }
    Page<UserResponseDto> studentsResponse = students.map(UserServiceImpl::toUserResponseDto);
    return UsersPageResponseDto.builder().users(studentsResponse).build();
  }

  private static UserResponseDto toUserResponseDto(UserEntity userEntity) {
    return UserResponseDto.builder()
        .id(userEntity.getId())
        .lastName(userEntity.getLastName())
        .firstName(userEntity.getFirstName())
        .email(userEntity.getEmail())
        .cnp(userEntity.getCnp())
        .build();
  }

  private void verifyCnpExists(UserCreateRequestDto userCreateRequestDto) {
    Optional<UserEntity> userEntity = userRepository.findByCnp(userCreateRequestDto.getCnp());
    if (userEntity.isPresent()) {
      throw new UserException(
          String.format("User with CNP: %s already exists", userCreateRequestDto.getCnp()));
    }
  }

  private void verifyEmailExists(UserCreateRequestDto userCreateRequestDto) {
    Optional<UserEntity> userEntity = userRepository.findByEmail(userCreateRequestDto.getEmail());
    if (userEntity.isPresent()) {
      throw new UserException(
          String.format("User with email %s already exists!", userCreateRequestDto.getEmail()));
    }
  }

  private void saveTeacher(UserCreateRequestDto userCreateRequestDto) {
    TeacherEntity teacherEntity =
        TeacherEntity.builder()
            .firstName(userCreateRequestDto.getFirstName())
            .lastName(userCreateRequestDto.getLastName())
            .email(userCreateRequestDto.getEmail())
            .password(userCreateRequestDto.getCnp())
            .role(UserRole.TEACHER)
            .cnp(userCreateRequestDto.getCnp())
            .password("password")
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
            .password("password")
            .build();

    studentRepository.save(studentEntity);
  }
}
