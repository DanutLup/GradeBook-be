package gradebook.api;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface LessonApi {
  public static final String LESSON_API_V1 = "/api/v1/lessons";

  @PostMapping(LESSON_API_V1)
  @ResponseStatus(HttpStatus.CREATED)
  void createLesson(@Valid @RequestBody CreateLessonRequestDto createLessonRequestDto);
}
