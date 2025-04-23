package gradebook.dto.course;

import gradebook.dto.user.StudentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
