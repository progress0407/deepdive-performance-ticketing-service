package numble.deepdive.performanceticketingservice.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoginRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
