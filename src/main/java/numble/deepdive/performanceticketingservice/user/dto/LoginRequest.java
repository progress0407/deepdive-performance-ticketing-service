package numble.deepdive.performanceticketingservice.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginRequest {
    private String email;
    private String password;
}
