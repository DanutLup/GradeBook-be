package gradebook.service;

import gradebook.dto.*;
import gradebook.exceptions.UserException;
import gradebook.repository.db.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CoursesResponseDto getCoursesForTeacher(int id){
        TeacherEntity teacher = teacherRepository.findById(id).orElseThrow();
        List<CourseEntity> courses = teacher.getCourses();

        return getCoursesResponseDto(courses.stream().map(getCourseEntityCourseForTeacherResponseDto()));
    }

    public CoursesResponseDto getCoursesForStudent(int id) {
        StudentEntity student = studentRepository.findById(id).orElseThrow();

        Stream<CourseResponseDto> coursesStream = student.getEnrollments().stream().map(enrollment -> {
            CourseResponseDto courseResponseDto = getCourseResponseDto(enrollment.getCourse());
            courseResponseDto.setGrade(enrollment.getGrade());
            return courseResponseDto;
        });
        return getCoursesResponseDto(coursesStream);
    }

    @Transactional
    public void createCourse(CourseRequestDto courseRequestDto){
        TeacherEntity teacher = teacherRepository.findById(courseRequestDto.getTeacherId())
                .orElseThrow(() -> new UserException("teacher not found"));
        CourseEntity courseEntity = CourseEntity.builder()
                .credits(courseRequestDto.getCredits())
                .teacher(teacher)
                .name(courseRequestDto.getName())
                .build();
        courseRepository.save(courseEntity);
    }

    public CoursesResponseDto getCourses() {
        List<CourseEntity> courses = courseRepository.findAll();
        return getCoursesResponseDto(courses.stream().map(CourseService::getCourseResponseDto));
    }

    private static CoursesResponseDto getCoursesResponseDto(Stream<CourseResponseDto> courses) {
        return CoursesResponseDto.builder()
                .courses(courses
                        .toList())
                .build();
    }

    private static Function<CourseEntity, CourseResponseDto> getCourseEntityCourseForTeacherResponseDto() {
        return courseEntity -> CourseResponseDto.builder()
                .credits(courseEntity.getCredits())
                .name(courseEntity.getName())
                .id(courseEntity.getId())
                .build();
    }

    private static CourseResponseDto getCourseResponseDto(CourseEntity courseEntity) {
        return CourseResponseDto.builder()
                .id(courseEntity.getId())
                .credits(courseEntity.getCredits())
                .name(courseEntity.getName())
                .teacher(getTeacherName(courseEntity))
                .build();
    }

    private static String getTeacherName(CourseEntity courseEntity) {
        return courseEntity.getTeacher().getFirstName() + " " + courseEntity.getTeacher().getLastName();
    }

    public void updateCourse(CourseRequestDto courseRequestDto) {
    CourseEntity courseEntity =
        courseRepository
            .findById(courseRequestDto.getCourseId())
            .orElseThrow(() -> new UserException("Course not found"));
    TeacherEntity teacher = teacherRepository.findById(courseRequestDto.getTeacherId())
            .orElseThrow(() -> new UserException("Teacher not found"));

    courseEntity.setCredits(courseRequestDto.getCredits());
    courseEntity.setName(courseRequestDto.getName());
    courseEntity.setTeacher(teacher);

    courseRepository.save(courseEntity);
    }

    public void deleteCourse(int courseId) {
        CourseEntity courseEntity = courseRepository.findById(courseId).orElseThrow(() -> new UserException("Course not found"));

        courseRepository.delete(courseEntity);
    }

    public CourseResponseDto getCourse(int id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new UserException("Course not found"));


        CourseResponseDto courseResponseDto = getCourseResponseDto(courseEntity);
        List<StudentEntity> students = courseEntity.getEnrollments().stream()
                .map(Enrollment::getStudent).toList();
        courseResponseDto.setStudents(students.stream().map(studentEntity -> StudentResponseDto.builder()
                .id(studentEntity.getId())
                .email(studentEntity.getEmail())
                .firstName(studentEntity.getFirstName())
                .lastName(studentEntity.getLastName())
                .grade(studentEntity.getEnrollments().stream()
                        .filter(enrollment -> enrollment.getCourse().equals(courseEntity))
                        .findFirst().orElseThrow(() -> new UserException("enrollment not found")).getGrade())
                .build()).toList());
        return courseResponseDto;
    }
}
