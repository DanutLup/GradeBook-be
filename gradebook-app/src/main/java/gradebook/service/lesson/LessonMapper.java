package gradebook.service.lesson;

import gradebook.dto.LessonTypeDto;
import gradebook.dto.request.lesson.CreateLessonRequestDto;
import gradebook.dto.request.lesson.UpdateLessonRequestDto;
import gradebook.dto.response.lesson.LessonResponseDto;
import gradebook.repository.db.data.LessonEntity;
import gradebook.repository.db.data.LessonType;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LessonMapper {

  public LessonEntity mapToLessonEntity(CreateLessonRequestDto createLessonRequestDto) {
    LessonEntity lessonEntity = new LessonEntity();
    lessonEntity.setType(LessonType.valueOf(createLessonRequestDto.getLessonType().name()));
    lessonEntity.setCreated(Date.valueOf(LocalDate.now()));
    lessonEntity.setContent(createLessonRequestDto.getContent());
    lessonEntity.setDescription(createLessonRequestDto.getDescription());
    lessonEntity.setTitle(createLessonRequestDto.getTitle());
    return lessonEntity;
  }

  public LessonEntity mapToLessonEntity(UpdateLessonRequestDto updateLessonRequestDto) {
    LessonEntity lessonEntity = new LessonEntity();
    lessonEntity.setType(LessonType.valueOf(updateLessonRequestDto.getLessonType().name()));
    lessonEntity.setCreated(Date.valueOf(LocalDate.now()));
    lessonEntity.setContent(updateLessonRequestDto.getContent());
    lessonEntity.setDescription(updateLessonRequestDto.getDescription());
    lessonEntity.setTitle(updateLessonRequestDto.getTitle());
    return lessonEntity;
  }

  public List<LessonResponseDto> mapToLessonsResponse(List<LessonEntity> lessons) {
    return lessons.stream().map(LessonMapper::mapToLessonResponseDto).toList();
  }

  private static LessonResponseDto mapToLessonResponseDto(LessonEntity lessonEntity) {
    return LessonResponseDto.builder()
        .lessonType(LessonTypeDto.valueOf(lessonEntity.getType().name()))
        .title(lessonEntity.getTitle())
        .description(lessonEntity.getDescription())
        .content(lessonEntity.getContent())
        .lessonId(lessonEntity.getId())
        .build();
  }
}
