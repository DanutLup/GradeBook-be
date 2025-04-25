package gradebook.dto.response.course;

import gradebook.dto.response.user.StudentResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseDto {
  private int id;
  private String name;
  private int credits;
  private String teacher;
  private int grade;
  private List<StudentResponseDto> students;
}
