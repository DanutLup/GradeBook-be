package gradebook.dto.request.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequestDto {
  private String firstName;
  private String lastName;
  private String email;
  private int id;
}
