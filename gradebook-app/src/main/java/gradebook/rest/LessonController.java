package gradebook.rest;

import gradebook.api.LessonApi;
import gradebook.dto.request.lesson.CreateLessonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LessonController implements LessonApi {

  @Override
  public void createLesson(CreateLessonRequestDto createLessonRequestDto) {}
}
