package gradebook.api;

import gradebook.dto.course.CourseRequestDto;
import gradebook.dto.course.CourseResponseDto;
import gradebook.dto.course.CoursesResponseDto;
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
    @ResponseStatus(HttpStatus.OK)
    CoursesResponseDto getCourses();

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
