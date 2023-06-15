package numble.deepdive.performanceticketingservice.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.user.domain.AbstractUser;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;

@NoArgsConstructor
@Getter
@ToString
public class UserResponse {

    private String name;

    private String email;

    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String businessLicense;

    public UserResponse(AbstractUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();

        if(user instanceof BusinessUser) {
            this.businessLicense = ((BusinessUser) user).getBusinessLicense();
        }
    }
}
