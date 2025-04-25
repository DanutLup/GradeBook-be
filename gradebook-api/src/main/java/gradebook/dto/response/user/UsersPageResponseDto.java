package gradebook.dto.response.user;

import lombok.*;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UsersPageResponseDto {
  private Page<UserResponseDto> users;
}
