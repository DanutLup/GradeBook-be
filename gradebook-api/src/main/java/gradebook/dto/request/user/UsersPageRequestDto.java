package gradebook.dto.request.user;

import gradebook.dto.user.UserRoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UsersPageRequestDto {
  private int pageNumber;
  private int pageSize;
  private String userName;
  private UserRoleDto role;
}
