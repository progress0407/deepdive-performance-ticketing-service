package numble.deepdive.performanceticketingservice.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.domain.User;

import java.io.Serializable;

@NoArgsConstructor
@Getter
public class UserCache implements Serializable {

    private Long id;
    private String email;
    private String name;
    private UserType userType;

    private enum UserType {
        GENERAL_USER, BUSINESS_USER
    }

    public UserCache(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userType = getUserType(user);
    }

    private static UserType getUserType(User user) {

        if (user instanceof GeneralUser) {
            return UserType.GENERAL_USER;
        }

        return UserType.BUSINESS_USER;
    }

    public boolean isGeneralUser() {

        return this.userType == UserType.GENERAL_USER;
    }

    public boolean isBusinessUser() {

        return this.userType == UserType.BUSINESS_USER;
    }
}
