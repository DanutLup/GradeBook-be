package gradebook.dto.response.lesson;

import gradebook.dto.LessonTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LessonResponseDto {
  private String title;
  private String description;
  private LessonTypeDto lessonType;
  private String content;
  private Integer lessonId;
  private Integer courseId;
}
