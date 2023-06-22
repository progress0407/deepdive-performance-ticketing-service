package numble.deepdive.performanceticketingservice.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.application.UserService;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.UserResponse;
import numble.deepdive.performanceticketingservice.user.dto.UserResponses;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/users")
    public long registerGeneralUser(@Valid @RequestBody GeneralUserCreateRequest request) {

        User user = request.toEntity();
        long userId = userService.registerUser(user);

        return userId;
    }

    @PostMapping("/businesses")
    public long registerBusinessUser(@Valid @RequestBody BusinessUserCreateRequest request) {

        User user = request.toEntity();
        long userId = userService.registerUser(user);

        return userId;
    }

    @GetMapping("/users")
    public UserResponses allUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponse> userResponsesCollection = convertUserResponsesCollection(users);

        return new UserResponses(userResponsesCollection);
    }


    private static List<UserResponse> convertUserResponsesCollection(List<User> users) {

        return users.stream()
                .map(UserResponse::new)
                .collect(toList());
    }
}
