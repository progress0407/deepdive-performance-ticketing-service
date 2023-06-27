package numble.deepdive.performanceticketingservice.user.dto;

import jakarta.validation.constraints.NotNull;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;

public record BusinessUserCreateRequest(

        @NotNull(message = "이메일은 필수입니다.")
        String email,

        @NotNull(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "비밀번호는 필수입니다.")
        String password,

        @NotNull(message = "사업자 등록번호는 필수입니다.")
        String businessLicense
) {

    public BusinessUser toEntity() {
        return new BusinessUser(name, email, password, businessLicense);
    }
}
