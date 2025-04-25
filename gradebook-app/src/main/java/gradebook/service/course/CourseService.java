package gradebook.service.course;

import gradebook.dto.request.course.CourseRequestDto;
import gradebook.dto.response.course.CourseResponseDto;
import gradebook.dto.response.course.CoursesResponseDto;

public interface CourseService {
  CoursesResponseDto getCoursesForTeacher(int id);

  CoursesResponseDto getCoursesForStudent(int id);

  void createCourse(CourseRequestDto courseRequestDto);

  CoursesResponseDto getCourses();

  void updateCourse(CourseRequestDto courseRequestDto);

  void deleteCourse(int courseId);

  CourseResponseDto getCourse(int id);
}
