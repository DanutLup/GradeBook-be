package gradebook.dto.request.user;

import gradebook.dto.user.UserRoleDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDeleteRequestDto {
  private int userId;
  private UserRoleDto role;
}
