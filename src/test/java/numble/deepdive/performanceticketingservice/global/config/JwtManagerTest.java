package numble.deepdive.performanceticketingservice.global.config;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerTest {

    static final String SECRET_KEY_STRING = "abc 123 abc 123 abc 123 abc 123 abc 123 abc 123 abc 123 abc 123 abc 123 abc 123";
    static final long ONE_DAY_IN_SECONDS = 86_400_000L; // 하루를 초로 표현한 것
    SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    JwtManager jwtManager = new JwtManager(secretKey, ONE_DAY_IN_SECONDS);

    @Test
    void 토큰을_만들고_검증한다() {
        // given
        String 원본_문자열 = "유일한 어떤 것";

        // when
        String accessToken = jwtManager.createAccessToken(원본_문자열);
        String 복호화한_값 = jwtManager.parse(accessToken);

        // then
        assertThat(복호화한_값).isEqualTo(원본_문자열);
    }
}







