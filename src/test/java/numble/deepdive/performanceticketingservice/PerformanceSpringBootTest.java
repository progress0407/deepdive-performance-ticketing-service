package numble.deepdive.performanceticketingservice;

import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceListResponses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class PerformanceSpringBootTest extends AcceptanceTest {

    String 일반_유저_토큰;
    String 사업자_토큰;
    long 공연장_ID;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();

        일반_회원가입();
        사업자_회원가입();

        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
        사업자_토큰 = 사업자_로그인_후_토큰_반환();

        공연장_ID = 공연장_생성요청(사업자_토큰).getId();
    }

    @Test
    void 일반인은_공연_등록을_할수없다() {
        // given
        PerformanceCreateRequest httpBody = performanceCreateRequest(공연장_ID);

        // when
        var response = post("/performances", httpBody, 일반_유저_토큰).extract();

        // then
        assertThat(response.statusCode()).isEqualTo(BAD_REQUEST.value());
    }

    @Test
    void 사업자는_공연_등록이_가능하다() {
        // given
        PerformanceCreateRequest httpBody = performanceCreateRequest(공연장_ID);

        // when
        var createResponse = post("/performances", httpBody, 사업자_토큰).extract();
        PerformanceCreateResponse 생성된_공연 = createResponse.as(PerformanceCreateResponse.class);
        long 공연_ID = 생성된_공연.getId();
        PerformanceListResponses responseBody = get("/performances", 사업자_토큰).extract().as(PerformanceListResponses.class);

        // then
        assertAll(
                () -> assertThat(createResponse.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(공연_ID).isPositive(),
                () -> assertThat(responseBody.getPerformances().get(0).getCapacity()).isEqualTo(2)
        );
    }
}