package numble.deepdive.performanceticketingservice;

import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

public class BookingSpringBootTest extends AcceptanceTest {

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
        var response = post("/bookings", httpBody, 사업자_토큰).extract();

        // then
        예외_검증(BAD_REQUEST, "사업자는 예매를 할 수 없습니다.", response);
    }


    @Test
    void 일반인은_공연_예매가_가능하다() {
        // given
        var httpBody = bookingCreateRequest(공연_ID);

        // when
        var response = post("/bookings", httpBody, 일반_유저_토큰).extract();
        long 예매_ID = response.as(BookingCreateResponse.class).getId();

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(CREATED.value()),
                () -> assertThat(예매_ID).isPositive()
        );
    }

    @Disabled
    @Test
    void 동일시간대에_같은_좌석을_예매할_수_없다() {
        // given
        var httpBody = bookingCreateRequest(공연장_ID);

        // when


        // then

    }

    @Disabled
    @Test
    void 동일시간대일지라도_다른_좌석이라면_예매할_수_있다() {
        // given

        // when

        // then

    }

    @Disabled
    @Test
    void 예매표의_가격과_공연에서_제시한_가격은_다를수없다() {
        // given

        // when

        // then

    }
}