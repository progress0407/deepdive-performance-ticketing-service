package numble.deepdive.performanceticketingservice.user.dto;

import jakarta.validation.constraints.NotNull;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;

public record GeneralUserCreateRequest(

        @NotNull(message = "이메일은 필수입니다.")
        String email,

        @NotNull(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "비밀번호는 필수입니다.")
        String password
) {

    public GeneralUser toEntity() {
        return new GeneralUser(name, email, password);
    }
}

