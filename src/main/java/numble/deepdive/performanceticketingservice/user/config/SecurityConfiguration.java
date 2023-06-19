package numble.deepdive.performanceticketingservice.user.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey secretKey(@Value("${jwt.secret-key}") String secretKey) {

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public JwtManager jwtManager(SecretKey secretKey,
                                 @Value("${jwt.expiration-duration-time}") long expirationDurationTime) {

        return new JwtManager(secretKey, expirationDurationTime);
    }

    @Bean
    public AuthInterceptor authInterceptor(JwtManager jwtManager) {

        return new AuthInterceptor(jwtManager);
    }
}
