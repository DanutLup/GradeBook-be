package gradebook.service.lesson;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;

public interface LessonService {
  void createLesson(CreateLessonRequestDto createLessonRequestDto);

  void updateLesson(UpdateLessonRequestDto updateLessonRequestDto);
}
