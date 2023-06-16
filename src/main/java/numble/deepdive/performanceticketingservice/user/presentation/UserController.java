package numble.deepdive.performanceticketingservice.user.presentation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.*;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.dto.*;
import numble.deepdive.performanceticketingservice.user.exception.NotMatchPasswordException;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Transactional
public class UserController {

    private final static SignatureAlgorithm ENCODING_ALGORITHM = SignatureAlgorithm.HS512;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final SecretKey secretKey;

    @Value("${jwt.expiration-duration-time}")
    private Long expirationDurationTime;

    @PostMapping("/businesses")
    public long registerBusinessUser(@Valid @RequestBody BusinessUserCreateRequest request) {

        BusinessUser user = request.toEntity();
        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }

    @PostMapping("/users")
    public long registerGeneralUser(@Valid @RequestBody GeneralUserCreateRequest request) {

        GeneralUser user = request.toEntity();
        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }

    @GetMapping("/users")
    public UserResponses allUsers() {

        List<User> users = userRepository.findAll();

        List<UserResponse> userResponsesCollection = convertUserResponsesCollection(users);

        return new UserResponses(userResponsesCollection);
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자 계정이 존재하지 않습니다"));

        if (isSamePassword(password, foundUser)) {
            String accessToken = createAccessToken(email);
            return new LoginResponse(accessToken);
        }

        throw new NotMatchPasswordException();
    }

    private String createAccessToken(String email) {

        return Jwts.builder()
                .signWith(secretKey, ENCODING_ALGORITHM)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(createExpirationDateTime())
                .compact();
    }

    private boolean isSamePassword(String inputRawPassword, User foundUser) {

        String inputPassword = passwordEncoder.encode(inputRawPassword);
        String dbPassword = foundUser.getPassword();

        return inputPassword.equals(dbPassword);
    }

    public Date createExpirationDateTime() {

        return new Date(System.currentTimeMillis() + expirationDurationTime);
    }

    private static List<UserResponse> convertUserResponsesCollection(List<User> users) {

        return users.stream()
                .map(UserResponse::new)
                .collect(toList());
    }
}
