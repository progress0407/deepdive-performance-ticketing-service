package numble.deepdive.performanceticketingservice.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Transactional
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/businesses")
    public long registerBusinessUser(@Valid @RequestBody BusinessUserCreateRequest request) {

        BusinessUser entity = request.toEntity();

        userRepository.save(entity);

        System.out.println("entity = " + entity);

        return entity.getId();
    }

    @PostMapping("/users")
    public long registerGeneralUser(@Valid @RequestBody UserCreateRequest request) {

        GeneralUser entity = request.toEntity();

        userRepository.save(entity);

        System.out.println("entity = " + entity);

        return entity.getId();
    }

    @GetMapping("/users")
    public UserResponses allUsers() {

        List<AbstractUser> users = userRepository.findAll();

        List<UserResponse> userResponsesCollection = convertUserResponsesCollection(users);

        return new UserResponses(userResponsesCollection);
    }

    private static List<UserResponse> convertUserResponsesCollection(List<AbstractUser> users) {
        return users.stream()
                .map(UserResponse::new)
                .collect(toList());
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class BusinessUserCreateRequest {

        @NotNull(message = "이름은 필수입니다.")
        private String name;

        @NotNull(message = "이메일은 필수입니다.")
        private String email;

        @NotNull(message = "비밀번호는 필수입니다.")
        private String password;

        @NotNull(message = "사업자 등록번호는 필수입니다.")
        private String businessLicense;

        @NotNull(message = "타입은 필수입니다.")
        private String type;

        public BusinessUser toEntity() {
            return new BusinessUser(name, email, password, businessLicense, type);
        }
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class UserCreateRequest {

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

    @NoArgsConstructor
    @Getter
    @ToString
    static class UserResponse {

        private String name;

        private String email;

        private String password;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String businessLicense;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String type;

        public UserResponse(AbstractUser user) {
            this.name = user.getName();
            this.email = user.getEmail();
            this.password = user.getPassword();

            if(user instanceof BusinessUser) {
                this.businessLicense = ((BusinessUser) user).getBusinessLicense();
                this.type = ((BusinessUser) user).getType();
            }
        }
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class UserResponses {

        private List<UserResponse> users;

        public UserResponses(List<UserResponse> list) {
            this.users = list;
        }
    }
}
