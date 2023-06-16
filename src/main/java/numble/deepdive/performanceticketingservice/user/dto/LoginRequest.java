package numble.deepdive.performanceticketingservice.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class LoginRequest {
    private String email;
    private String password;
}
