package gradebook.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatisticsResponseDto {

  private int numberOfStudents;
  private int numberOfTeachers;
  private int numberOfCourses;
}
