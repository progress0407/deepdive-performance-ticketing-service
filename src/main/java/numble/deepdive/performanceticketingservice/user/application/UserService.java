package numble.deepdive.performanceticketingservice.user.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.config.JwtManager;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.exception.NotMatchPasswordException;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtManager jwtManager;

    public long registerUser(User user) {

        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }


    public String login(String email, String inputRawPassword) {

        String dbPassword = foundDbPassword(email);

        if (isSamePassword(inputRawPassword, dbPassword)) {
            return jwtManager.createAccessToken(email);
        }

        throw new NotMatchPasswordException();
    }

    private String foundDbPassword(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 계정이 존재하지 않습니다"))
                .getPassword();
    }

    private boolean isSamePassword(String inputRawPassword, String dbPassword) {

        return passwordEncoder.matches(inputRawPassword, dbPassword);
    }
}
