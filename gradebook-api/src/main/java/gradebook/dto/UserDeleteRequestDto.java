package gradebook.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDeleteRequestDto {
    private int userId;
    private UserRoleDto role;
}
