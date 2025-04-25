package gradebook.api;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.dto.response.lesson.LessonResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public interface LessonApi {
  String LESSON_API_V1 = "/api/v1/lessons";

  @PostMapping(LESSON_API_V1)
  @ResponseStatus(HttpStatus.CREATED)
  void createLesson(@Valid @RequestBody CreateLessonRequestDto createLessonRequestDto);

  @PutMapping(LESSON_API_V1)
  @ResponseStatus(HttpStatus.OK)
  void updateLesson(@Valid @RequestBody UpdateLessonRequestDto updateLessonRequestDto);

  @GetMapping(LESSON_API_V1 + "/course/{courseId}")
  @ResponseStatus(HttpStatus.OK)
  List<LessonResponseDto> getLessons(@PathVariable Integer courseId);

  @DeleteMapping(LESSON_API_V1 + "/{lessonId}")
  @ResponseStatus(HttpStatus.OK)
  void deleteLesson(@PathVariable Integer lessonId);
}
