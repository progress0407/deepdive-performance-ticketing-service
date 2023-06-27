package numble.deepdive.performanceticketingservice.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.User;

public record UserResponse(

        String name,

        String email,

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
        if (user instanceof BusinessUser) {
            return ((BusinessUser) user).getBusinessLicense();
        }
        return null;
    }
}
