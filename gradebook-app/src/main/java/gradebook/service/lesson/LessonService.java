package gradebook.service.lesson;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.dto.response.lesson.LessonResponseDto;
import java.util.List;

public interface LessonService {
  void createLesson(CreateLessonRequestDto createLessonRequestDto);

  void updateLesson(UpdateLessonRequestDto updateLessonRequestDto);

  List<LessonResponseDto> getLessons(Integer id);

  void deleteLesson(Integer lessonId);
}
