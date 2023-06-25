package numble.deepdive.performanceticketingservice.acceptance;

import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class BookingAcceptanceTest extends AcceptanceTest {

    String 일반_유저_토큰;
    String 사업자_토큰;
    long 공연장_ID;
    long 공연_ID;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();

        일반_회원가입();
        사업자_회원가입();

        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
        사업자_토큰 = 사업자_로그인_후_토큰_반환();

        공연장_ID = 공연장_생성요청(사업자_토큰).getId();
        공연_ID = 공연_생성요청(공연장_ID, 사업자_토큰).getId();
    }

    @Test
    void 사업자는_공연_예매를_할수없다() {
        // given
        var httpBody = bookingCreateRequest(공연_ID);

        // when
        var response = post("/bookings", httpBody, 사업자_토큰);

        // then
        예외_검증(BAD_REQUEST, "사업자는 예매를 할 수 없습니다.", response);
    }


    @Test
    void 일반인은_공연_예매가_가능하다() {
        // given
        var httpBody = bookingCreateRequest(공연_ID);

        // when
        var response = post("/bookings", httpBody, 일반_유저_토큰);
        long 예매_ID = response.as(BookingCreateResponse.class).getId();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(예매_ID).isPositive()
        );
    }

    @Test
    void 예매표의_가격과_공연에서_제시한_가격은_다를수없다() {
        // given
        var 잘못된_가격 = 1_000;
        var httpBody = bookingWrongTotalPriceCreateRequest(공연_ID, 잘못된_가격);

        // when
        var response = post("/bookings", httpBody, 일반_유저_토큰);

        // then
        예외_검증(BAD_REQUEST, "총 가격이 일치하지 않습니다.", response);
    }

    @Test
    void 동일_공연의_같은_좌석을_예매할_수_없다() {
        // given
        var httpBody1 = bookingCreateRequest(공연_ID, Map.of("A1", "VIP", "B1", "GENERAL"), 60_000);
        var httpBody2 = bookingCreateRequest(공연_ID, Map.of("A1", "VIP"), 50_000);

        // when
        post("/bookings", httpBody1, 일반_유저_토큰);
        var response = post("/bookings", httpBody2, 일반_유저_토큰);


        // then
        예외_검증(BAD_REQUEST, "이미 예약된 좌석이 포함되어 있습니다.", response);
    }

    @Test
    void 동일_공연의_다른_좌석은_예매할_수_있다() {
        // given
        var httpBody1 = bookingCreateRequest(공연_ID, Map.of("A1", "VIP", "B1", "GENERAL"), 60_000);
        var httpBody2 = bookingCreateRequest(공연_ID, Map.of("A2", "VIP"), 50_000);

        // when
        post("/bookings", httpBody1, 일반_유저_토큰);
        var response = post("/bookings", httpBody2, 일반_유저_토큰);
        long 예매_ID = response.as(BookingCreateResponse.class).getId();


        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(예매_ID).isPositive()
        );

    }
}