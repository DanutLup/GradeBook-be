package gradebook.service.course;

import gradebook.dto.course.CourseResponseDto;
import gradebook.dto.course.CoursesResponseDto;
import gradebook.dto.user.StudentResponseDto;
import gradebook.exceptions.UserException;
import gradebook.repository.db.data.CourseEntity;
import gradebook.repository.db.data.StudentEntity;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.stream.Stream;

@UtilityClass
public class CourseMapper {

    public CoursesResponseDto getCoursesResponseDto(Stream<CourseResponseDto> courses) {
        return CoursesResponseDto.builder()
                .courses(courses
                        .toList())
                .build();
    }

    public Function<CourseEntity, CourseResponseDto> getCourseEntityCourseForTeacherResponseDto() {
        return courseEntity -> CourseResponseDto.builder()
                .credits(courseEntity.getCredits())
                .name(courseEntity.getName())
                .id(courseEntity.getId())
                .build();
    }

    public CourseResponseDto getCourseResponseDto(CourseEntity courseEntity) {
        return CourseResponseDto.builder()
                .id(courseEntity.getId())
                .credits(courseEntity.getCredits())
                .name(courseEntity.getName())
                .teacher(getTeacherName(courseEntity))
                .build();
    }

    public String getTeacherName(CourseEntity courseEntity) {
        return courseEntity.getTeacher().getFirstName() + " " + courseEntity.getTeacher().getLastName();
    }

    public StudentResponseDto getStudentResponseDto(StudentEntity studentEntity, CourseEntity courseEntity) {
        return StudentResponseDto.builder()
                .id(studentEntity.getId())
                .email(studentEntity.getEmail())
                .firstName(studentEntity.getFirstName())
                .lastName(studentEntity.getLastName())
                .grade(
                        studentEntity.getEnrollmentEntities().stream()
                                .filter(enrollment -> enrollment.getCourse().equals(courseEntity))
                                .findFirst()
                                .orElseThrow(() -> new UserException("enrollment not found"))
                                .getGrade())
                .build();
    }
}
