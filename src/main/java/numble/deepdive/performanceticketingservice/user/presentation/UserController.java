package numble.deepdive.performanceticketingservice.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.AbstractUser;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.UserResponse;
import numble.deepdive.performanceticketingservice.user.dto.UserResponses;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
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

        return entity.getId();
    }

    @PostMapping("/users")
    public long registerGeneralUser(@Valid @RequestBody GeneralUserCreateRequest request) {

        GeneralUser entity = request.toEntity();

        userRepository.save(entity);

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
}
