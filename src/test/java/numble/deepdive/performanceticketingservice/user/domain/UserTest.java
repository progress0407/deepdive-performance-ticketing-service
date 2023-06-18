package numble.deepdive.performanceticketingservice.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void 암호화한_비밀번호를_원문_비밀번호와_비교() {
        // given
        String 비밀번호_원문 = "1234";
        User user = new GeneralUser("철수", "philz@mail.com", 비밀번호_원문);

        // when
        user.encodePassword(passwordEncoder);

        // then
        String 암호화된_비밀번호 = user.getPassword();

        assertThat(passwordEncoder.matches(비밀번호_원문, 암호화된_비밀번호)).isTrue();
    }
}