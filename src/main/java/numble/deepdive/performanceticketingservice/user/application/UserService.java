package numble.deepdive.performanceticketingservice.user.application;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.exception.NotMatchPasswordException;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final static SignatureAlgorithm ENCODING_ALGORITHM = SignatureAlgorithm.HS512;

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SecretKey secretKey;

    @Value("${jwt.expiration-duration-time}")
    private Long expirationDurationTime;

    public long registerUser(User user) {

        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }

    public Date createExpirationDateTime() {

        return new Date(System.currentTimeMillis() + expirationDurationTime);
    }

    public String login(String email, String inputRawPassword) {

        String dbPassword = foundDbPassword(email);

        if (isSamePassword(inputRawPassword, dbPassword)) {
            return createAccessToken(email);
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

    private String createAccessToken(String email) {

        return Jwts.builder()
                .signWith(secretKey, ENCODING_ALGORITHM)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(createExpirationDateTime())
                .compact();
    }
}
