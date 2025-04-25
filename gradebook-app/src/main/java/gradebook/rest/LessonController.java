package gradebook.rest;

import gradebook.api.LessonApi;
import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.dto.response.lesson.LessonResponseDto;
import gradebook.service.lesson.LessonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LessonController implements LessonApi {
  private final LessonService lessonService;

  @Override
  public void createLesson(CreateLessonRequestDto createLessonRequestDto) {
    lessonService.createLesson(createLessonRequestDto);
  }

  @Override
  public void updateLesson(UpdateLessonRequestDto updateLessonRequestDto) {
    lessonService.updateLesson(updateLessonRequestDto);
  }

  @Override
  public List<LessonResponseDto> getLessons(Integer courseId) {
    return lessonService.getLessons(courseId);
  }

  @Override
  public void deleteLesson(Integer lessonId) {
    lessonService.deleteLesson(lessonId);
  }
}
