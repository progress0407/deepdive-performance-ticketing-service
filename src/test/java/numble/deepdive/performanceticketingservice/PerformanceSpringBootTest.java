package numble.deepdive.performanceticketingservice;

import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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

        공연장_ID = 공연장_생성요청().getId();
    }

    @Test
    void 일반인은_공연_예매를_할수없다() {
        // given
        PerformanceCreateRequest httpBody = performanceCreateRequest();

        // when
        var response = post("/performances", httpBody, 일반_유저_토큰).extract();

        // then
        HTTP코드_검증(response, BAD_REQUEST);
    }

    @Test
    void 사업자는_공연_예매가_가능하다() {
        // given
        PerformanceCreateRequest httpBody = performanceCreateRequest();

        // when
        var response = post("/performances", httpBody, 사업자_토큰).extract();

        // then
        assertAll(
                () -> HTTP코드_검증(response, CREATED),
                () -> assertThat(공연장_ID).isPositive()
        );
    }

    private VenueCreateResponse 공연장_생성요청() {

        return post("/venues", createVenueCreateRequest(), 사업자_토큰).extract().as(VenueCreateResponse.class);
    }

    private VenueCreateRequest createVenueCreateRequest() {

        var seatRequests = List.of(
                new SeatCreateRequest("A1", "GENERAL"),
                new SeatCreateRequest("A2", "GENERAL")
        );

        return new VenueCreateRequest("[테스트 용도] 어떤 한 공연장", seatRequests);
    }

    private PerformanceCreateRequest performanceCreateRequest() {

        return new PerformanceCreateRequest(공연장_ID,
                "이무진 공연",
                "2023-06-30",
                "14:00",
                "16:00",
                Map.of("VIP", 50000, "일반", 10000)
        );
    }
}