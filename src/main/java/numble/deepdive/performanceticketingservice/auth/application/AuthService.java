package numble.deepdive.performanceticketingservice.auth.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.auth.config.JwtManager;
import numble.deepdive.performanceticketingservice.auth.exception.NotMatchPasswordException;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtManager jwtManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public String login(String email, String inputRawPassword) {

        String dbPassword = findDbPassword(email);

        if (isSamePassword(inputRawPassword, dbPassword)) {
            return jwtManager.createAccessToken(email);
        }

        throw new NotMatchPasswordException();
    }
    private String findDbPassword(String email) {

        return userRepository.findByEmailOrThrow(email)
                .getPassword();
    }

    private boolean isSamePassword(String inputRawPassword, String dbPassword) {

        return passwordEncoder.matches(inputRawPassword, dbPassword);
    }
}
