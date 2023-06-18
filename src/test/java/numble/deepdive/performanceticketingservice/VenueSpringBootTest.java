package numble.deepdive.performanceticketingservice;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import numble.deepdive.performanceticketingservice.user.dto.BusinessUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.GeneralUserCreateRequest;
import numble.deepdive.performanceticketingservice.user.dto.LoginRequest;
import numble.deepdive.performanceticketingservice.user.dto.LoginResponse;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class VenueSpringBootTest extends AcceptanceTest {

    String 일반_유저_토큰;
    String 사업자_토큰;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        일반_회원가입();
        사업자_회원가입();

        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
        사업자_토큰 = 사업자_로그인_후_토큰_반환();
    }

    @Test
    void 사업자는_공연장_등록이_가능하다() {
        // given
        var request = createVenueCreateRequest();

        // when
        var response = post("/venue", request, 사업자_토큰).extract();

        // then
        long createdId = response.as(VenueCreateResponse.class).getId();

        assertAll(
                () -> HTTP코드_검증(response, CREATED),
                () -> assertThat(createdId).isPositive()
        );
    }

    @Test
    void 일반_유저는_공연장_등록이_불가능하다() {
        // given
        var request = createVenueCreateRequest();

        // when
        var response = post("/venue", request, 일반_유저_토큰).extract();

        HTTP코드_검증(response, BAD_REQUEST);
    }

    private static void HTTP코드_검증(ExtractableResponse<Response> response, HttpStatus httpStatus) {

        assertThat(response.statusCode()).isEqualTo(httpStatus.value());
    }

    private String 사업자_로그인_후_토큰_반환() {
        return post("/login", new LoginRequest("philz_biz@gmail.com", "password")).extract().as(LoginResponse.class).getAccessToken();
    }

    private String 일반_유저_로그인_후_토큰_반환() {
        return post("/login", new LoginRequest("philz@gmail.com", "password")).extract().as(LoginResponse.class).getAccessToken();
    }

    private void 사업자_회원가입() {
        var businessUserCreateRequest = new BusinessUserCreateRequest("philz_biz@gmail.com", "sw cho", "password", "1234-5678");
        registerBusinessUser(businessUserCreateRequest);
    }

    private void 일반_회원가입() {
        var generalUserCreateRequest = new GeneralUserCreateRequest("philz@gmail.com", "sw cho", "password");
        registerSampleUser(generalUserCreateRequest);
    }

    private VenueCreateRequest createVenueCreateRequest() {

        var seatRequests = new ArrayList<SeatCreateRequest>();
        seatRequests.add(new SeatCreateRequest("A1", "GENERAL"));
        seatRequests.add(new SeatCreateRequest("A2", "GENERAL"));

        return new VenueCreateRequest("[테스트 용도] 어떤 한 공연장", seatRequests);
    }

    private void registerSampleUser(GeneralUserCreateRequest request) {

        post("/users", request);
    }

    private void registerBusinessUser(BusinessUserCreateRequest request) {

        post("/businesses", request);
    }
}
