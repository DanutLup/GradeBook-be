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

  @Override
  public CoursesResponseDto getCoursesForTeacher(int id) {
    TeacherEntity teacher = teacherRepository.findById(id).orElseThrow();
    List<CourseEntity> courses = teacher.getCourses();

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
            .orElseThrow(() -> new UserException("teacher not found"));
    CourseEntity courseEntity =
        CourseEntity.builder()
            .credits(courseRequestDto.getCredits())
            .teacher(teacher)
            .name(courseRequestDto.getName())
            .build();
    courseRepository.save(courseEntity);
  }

  @Override
  public CoursesResponseDto getCourses() {
    List<CourseEntity> courses = courseRepository.findAll();
    return getCoursesResponseDto(courses.stream().map(CourseMapper::getCourseResponseDto));
  }

  @Override
  public void updateCourse(CourseRequestDto courseRequestDto) {
    CourseEntity courseEntity =
        courseRepository
            .findById(courseRequestDto.getCourseId())
            .orElseThrow(() -> new UserException("Course not found"));
    TeacherEntity teacher =
        teacherRepository
            .findById(courseRequestDto.getTeacherId())
            .orElseThrow(() -> new UserException("Teacher not found"));

    courseEntity.setCredits(courseRequestDto.getCredits());
    courseEntity.setName(courseRequestDto.getName());
    courseEntity.setTeacher(teacher);

    courseRepository.save(courseEntity);
  }

  @Override
  public void deleteCourse(int courseId) {
    CourseEntity courseEntity =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new UserException("Course not found"));

    courseRepository.delete(courseEntity);
  }

  @Override
  public CourseResponseDto getCourse(int id) {
    CourseEntity courseEntity =
        courseRepository.findById(id).orElseThrow(() -> new UserException("Course not found"));

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
