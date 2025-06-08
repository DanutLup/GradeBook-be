package gradebook.api;

import gradebook.dto.request.course.CourseRequestDto;
import gradebook.dto.response.course.CourseResponseDto;
import gradebook.dto.response.course.CoursesResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface CourseApi {
  @GetMapping("/get-courses-for-teacher/{id}")
  @ResponseStatus(HttpStatus.OK)
  CoursesResponseDto getCoursesForTeacher(@PathVariable int id);

  @GetMapping("/get-courses-for-student/{id}")
  @ResponseStatus(HttpStatus.OK)
  CoursesResponseDto getCoursesForStudent(@PathVariable int id);

  @PostMapping("/create-course")
  @ResponseStatus(HttpStatus.OK)
  void createCourse(@RequestBody CourseRequestDto courseRequestDto);

  @GetMapping("/get-courses")
  CoursesResponseDto getCourses(@RequestParam(required = false) String courseName);

  @GetMapping("/get-course/{id}")
  @ResponseStatus(HttpStatus.OK)
  CourseResponseDto getCourse(@PathVariable int id);

  @PutMapping("/update-course")
  @ResponseStatus(HttpStatus.OK)
  void updateCourse(@RequestBody CourseRequestDto courseRequestDto);

  @DeleteMapping("/delete-course/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteCourse(@PathVariable int courseId);
}
