package gradebook.rest;

import gradebook.api.CourseApi;
import gradebook.dto.request.course.CourseRequestDto;
import gradebook.dto.response.course.CourseResponseDto;
import gradebook.dto.response.course.CoursesResponseDto;
import gradebook.service.course.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi {
  private final CourseService courseService;

  @Override
  public CoursesResponseDto getCoursesForTeacher(int id) {
    return courseService.getCoursesForTeacher(id);
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
  public CoursesResponseDto getCourses() {
    return courseService.getCourses();
  }

  @Override
  public CourseResponseDto getCourse(int id) {
    return courseService.getCourse(id);
  }

  @Override
  public void updateCourse(CourseRequestDto courseRequestDto) {
    courseService.updateCourse(courseRequestDto);
  }

  @Override
  public void deleteCourse(int courseId) {
    courseService.deleteCourse(courseId);
  }
}
