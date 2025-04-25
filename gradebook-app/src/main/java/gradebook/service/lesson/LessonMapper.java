package gradebook.service.lesson;

import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.repository.db.data.LessonEntity;
import gradebook.repository.db.data.LessonType;
import java.sql.Date;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LessonMapper {

  public LessonEntity mapToLessonEntity(CreateLessonRequestDto createLessonRequestDto) {
    LessonEntity lessonEntity = new LessonEntity();
    lessonEntity.setType(LessonType.valueOf(createLessonRequestDto.getLessonType().name()));
    lessonEntity.setCreated(Date.valueOf(LocalDate.now()));
    lessonEntity.setContent(createLessonRequestDto.getContent());
    lessonEntity.setDescription(createLessonRequestDto.getDescription());
    return lessonEntity;
  }

  public LessonEntity mapToLessonEntity(UpdateLessonRequestDto updateLessonRequestDto) {
    LessonEntity lessonEntity = new LessonEntity();
    lessonEntity.setType(LessonType.valueOf(updateLessonRequestDto.getLessonType().name()));
    lessonEntity.setCreated(Date.valueOf(LocalDate.now()));
    lessonEntity.setContent(updateLessonRequestDto.getContent());
    lessonEntity.setDescription(updateLessonRequestDto.getDescription());
    return lessonEntity;
  }
}
