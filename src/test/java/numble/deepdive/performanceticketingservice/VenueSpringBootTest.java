package numble.deepdive.performanceticketingservice;

import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import numble.deepdive.performanceticketingservice.venue.dto.VenueListResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class VenueSpringBootTest extends AcceptanceTest {

    String 일반_유저_토큰;
    String 사업자_토큰;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        일반_회원가입();
        사업자_회원가입();

        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
        사업자_토큰 = 사업자_로그인_후_토큰_반환();
    }

    @Test
    void 일반_유저는_공연장_등록이_불가능하다() {
        // given
        var httpBody = venueCreateRequest();

        // when
        var response = post("/venues", httpBody, 일반_유저_토큰).extract();

        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    void 사업자는_공연장_등록이_가능하다() {
        // given
        var httpBody = venueCreateRequest();

        // when
        var response = post("/venues", httpBody, 사업자_토큰).extract();

        // then
        long createdId = response.as(VenueCreateResponse.class).getId();
        var responseBody = get("/venues", 사업자_토큰).extract().as(VenueListResponses.class);
        int responseSeatCount = responseBody.getVenues().get(0).getSeatCount();

        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(createdId).isPositive(),
                () -> assertThat(responseSeatCount).isEqualTo(4)
        );
    }
}
