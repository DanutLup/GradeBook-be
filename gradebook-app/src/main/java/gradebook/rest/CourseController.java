package gradebook.rest;

import gradebook.api.CourseApi;
import gradebook.dto.request.course.CourseRequestDto;
import gradebook.dto.response.course.CourseResponseDto;
import gradebook.dto.response.course.CoursesResponseDto;
import gradebook.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CourseApi {
  private final CourseService courseService;

  @Override
  public CoursesResponseDto getCoursesForTeacher(int id, String courseName) {
    return courseService.getCoursesForTeacher(id, courseName);
  }

  @Override
  public CoursesResponseDto getCoursesForStudent(int id) {
    return courseService.getCoursesForStudent(id);
  }

  @Override
  public void createCourse(CourseRequestDto courseRequestDto) {
    courseService.createCourse(courseRequestDto);
  }

  @Override
  public CoursesResponseDto getCourses(String courseName) {
    return courseService.getCourses(courseName);
  }

  @Override
  public CourseResponseDto getCourse(int id) {
    return courseService.getCourse(id);
  }

  @Override
  public void updateCourse(CourseRequestDto courseRequestDto) {
    log.info("Received request to updateCourse: {}", courseRequestDto);
    courseService.updateCourse(courseRequestDto);
  }

  @Override
  public void deleteCourse(int courseId) {
    courseService.deleteCourse(courseId);
  }
}
