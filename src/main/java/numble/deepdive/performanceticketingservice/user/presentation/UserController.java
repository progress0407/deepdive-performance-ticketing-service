package numble.deepdive.performanceticketingservice.user.presentation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import lombok.*;
import numble.deepdive.performanceticketingservice.user.domain.AbstractUser;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.UserResponse;
import numble.deepdive.performanceticketingservice.user.dto.UserResponses;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SecretKey secretKey;

    @Value("${jwt.expiration-duration-time}")
    private Long expirationDurationTime;

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

    @PostMapping("/login")
    @ResponseStatus(OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        String email = request.getEmail();
        String password = request.getPassword();

        AbstractUser foundUser = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자 계정이 존재하지 않습니다"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (isSamePassword(password, foundUser)) {

            String accessToken = createAccessToken(email);

            return new LoginResponse(accessToken);
        }
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    private String createAccessToken(String email) {
        return Jwts.builder()
                .signWith(secretKey, ENCODING_ALGORITHM)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(createExpirationDateTime())
                .compact();
    }

    private static boolean isSamePassword(String password, AbstractUser foundUser) {
        return foundUser.getPassword().equals(password);
    }

    public Date createExpirationDateTime() {

        return new Date(System.currentTimeMillis() + expirationDurationTime);
    }

    @NoArgsConstructor
    @Getter
    @ToString
    static class LoginRequest {
        private String email;
        private String password;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    static class LoginResponse {
        private String accessToken;
    }

    private static List<UserResponse> convertUserResponsesCollection(List<AbstractUser> users) {

        return users.stream()
                .map(UserResponse::new)
                .collect(toList());
    }
}
