package gradebook.service.course;

import static gradebook.service.course.CourseMapper.*;

import gradebook.dto.request.course.CourseRequestDto;
import gradebook.dto.response.course.CourseResponseDto;
import gradebook.dto.response.course.CoursesResponseDto;
import gradebook.exception.UserException;
import gradebook.repository.db.*;
import gradebook.repository.db.data.CourseEntity;
import gradebook.repository.db.data.EnrollmentEntity;
import gradebook.repository.db.data.StudentEntity;
import gradebook.repository.db.data.TeacherEntity;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {
  private final CourseRepository courseRepository;
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;
  private final EnrollmentRepository enrollmentRepository;

  @Override
  public CoursesResponseDto getCoursesForTeacher(int id, String courseName) {
    TeacherEntity teacher = teacherRepository.findById(id).orElseThrow();
    List<CourseEntity> courses = teacher.getCourses();
    if (StringUtils.isNotBlank(courseName)) {
      List<CourseEntity> filteredCourses =
          courses.stream()
              .filter(
                  courseEntity ->
                      courseEntity.getName().toLowerCase().contains(courseName.toLowerCase()))
              .toList();
      return getCoursesResponseDto(
          filteredCourses.stream().map(getCourseEntityCourseForTeacherResponseDto()));
    }

    return getCoursesResponseDto(
        courses.stream().map(getCourseEntityCourseForTeacherResponseDto()));
  }

  @Override
  public CoursesResponseDto getCoursesForStudent(int id) {
    StudentEntity student = studentRepository.findById(id).orElseThrow();

    Stream<CourseResponseDto> coursesStream =
        student.getEnrollmentEntities().stream()
            .map(
                enrollment -> {
                  CourseResponseDto courseResponseDto =
                      getCourseResponseDto(enrollment.getCourse());
                  courseResponseDto.setGrade(enrollment.getGrade());
                  return courseResponseDto;
                });
    return getCoursesResponseDto(coursesStream);
  }

  @Transactional
  @Override
  public void createCourse(CourseRequestDto courseRequestDto) {
    TeacherEntity teacher =
        teacherRepository
            .findById(courseRequestDto.getTeacherId())
            .orElseThrow(() -> new UserException("Profesorul nu a fost găsit"));
    CourseEntity courseEntity =
        CourseEntity.builder()
            .credits(courseRequestDto.getCredits())
            .teacher(teacher)
            .name(courseRequestDto.getName())
            .build();
    courseRepository.save(courseEntity);
  }

  @Override
  public CoursesResponseDto getCourses(String courseName) {
    List<CourseEntity> courses;
    if (StringUtils.isNotBlank(courseName)) {
      courses = courseRepository.findByNameContainingIgnoreCase(courseName);
    } else {
      courses = courseRepository.findAll();
    }
    return getCoursesResponseDto(courses.stream().map(CourseMapper::getCourseResponseDto));
  }

  @Override
  @Transactional
  public void updateCourse(CourseRequestDto courseRequestDto) {
    CourseEntity courseEntity =
        courseRepository
            .findById(courseRequestDto.getCourseId())
            .orElseThrow(() -> new UserException("Cursul nu a fost găsit"));
    TeacherEntity teacher =
        teacherRepository
            .findById(courseRequestDto.getTeacherId())
            .orElseThrow(() -> new UserException("Profesorul nu a fost găsit"));

    courseEntity.setCredits(courseRequestDto.getCredits());
    courseEntity.setName(courseRequestDto.getName());
    courseEntity.setTeacher(teacher);

    courseRepository.save(courseEntity);
  }

  @Override
  @Transactional
  public void deleteCourse(int courseId) {
    CourseEntity courseEntity =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new UserException("Cursul nu a fost găsit"));

    courseEntity.setTeacher(null);
    var enrollments = courseEntity.getEnrollmentEntities();
    enrollmentRepository.deleteAll(enrollments);
    courseRepository.delete(courseEntity);
  }

  @Override
  public CourseResponseDto getCourse(int id) {
    CourseEntity courseEntity =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new UserException("Cursul nu a fost găsit"));

    CourseResponseDto courseResponseDto = getCourseResponseDto(courseEntity);
    List<StudentEntity> students =
        courseEntity.getEnrollmentEntities().stream().map(EnrollmentEntity::getStudent).toList();
    courseResponseDto.setStudents(
        students.stream()
            .map(studentEntity -> getStudentResponseDto(studentEntity, courseEntity))
            .toList());
    return courseResponseDto;
  }
}
