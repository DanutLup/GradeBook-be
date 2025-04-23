package gradebook.dto.user;

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
    private UserRoleDto role;
}
