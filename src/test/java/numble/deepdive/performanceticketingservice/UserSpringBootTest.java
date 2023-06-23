package numble.deepdive.performanceticketingservice;

import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.auth.dto.LoginRequest;
import numble.deepdive.performanceticketingservice.auth.dto.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserSpringBootTest extends AcceptanceTest {

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();

        var httpBody = new GeneralUserCreateRequest("philz@gmail.com", "sw cho", "password");

        registerSampleUser(httpBody);
    }

    @Test
    void 로그인_성공시_토큰값을_가져온다() {

        var loginRequest = new LoginRequest("philz@gmail.com", "password");

        LoginResponse response = post("/login", loginRequest).extract().as(LoginResponse.class);

        assertThat(response.getAccessToken()).isNotBlank();
    }
}
