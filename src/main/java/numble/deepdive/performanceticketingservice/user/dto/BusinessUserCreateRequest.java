package numble.deepdive.performanceticketingservice.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BusinessUserCreateRequest {

    @NotNull(message = "이메일은 필수입니다.")
    private String email;

    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;

    @NotNull(message = "사업자 등록번호는 필수입니다.")
    private String businessLicense;

    public BusinessUser toEntity() {
        return new BusinessUser(name, email, password, businessLicense);
    }
}
