package gradebook.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDeleteRequestDto {
    private int userId;
    private UserRoleDto role;
}
