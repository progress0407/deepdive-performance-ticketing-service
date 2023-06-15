package numble.deepdive.performanceticketingservice.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class UserResponses {

    private List<UserResponse> users;

    public UserResponses(List<UserResponse> list) {
        this.users = list;
    }
}
