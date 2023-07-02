package numble.deepdive.performanceticketingservice.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.User;

public record UserResponse(

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String name,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String email,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String password,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String businessLicense
) {

    public UserResponse(User user) {
        this(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                extractBusinessLicense(user)
        );
    }

    private static String extractBusinessLicense(User user) {
        if (user instanceof BusinessUser u){
            return u.getBusinessLicense();
        }
        return null;
    }
}
