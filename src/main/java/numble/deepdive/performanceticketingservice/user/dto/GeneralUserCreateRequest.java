package numble.deepdive.performanceticketingservice.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;

@NoArgsConstructor
@Getter
@ToString
public class GeneralUserCreateRequest {

    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;

    public GeneralUser toEntity() {
        return new GeneralUser(name, email, password);
    }
}

