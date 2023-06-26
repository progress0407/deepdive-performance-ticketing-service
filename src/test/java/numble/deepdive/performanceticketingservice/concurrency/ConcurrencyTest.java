package numble.deepdive.performanceticketingservice.concurrency;

import numble.deepdive.performanceticketingservice.acceptance.AcceptanceTest;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.infrastructure.BookingRepository;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceSeatRepository;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {
                "classpath:sql/concurrency-others.sql",
                "classpath:sql/concurrency-venue-seat.sql",
                "classpath:sql/concurrency-performance-seat.sql"
        }
)
public class ConcurrencyTest extends AcceptanceTest {

    long 공연_ID = 10_001L;
    String 일반_유저_토큰;
    CountDownLatch latch = new CountDownLatch(1_000);
    ExecutorService threadPool = Executors.newFixedThreadPool(1_000);

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();

        일반_회원가입();
        일반_유저_토큰 = 일반_유저_로그인_후_토큰_반환();
    }

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceSeatRepository performanceSeatRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Test
    void 동시_예약을_한_경우에_대해_Thread_Safe한지를_테스트한다() throws InterruptedException {
        // when
        for (int i = 1; i <= 250; i++) {

            String seatNumber = String.format("A%d", i);
            var httpBody = bookingCreateRequest(공연_ID, Map.of(seatNumber, "GENERAL"), 10_000);

            for (int j = 1; j <= 4; j++) {
                비동기_요청(httpBody);
            }
        }

        latch.await(); // 모든 요청이 끝나기를 기다린다.
        threadPool.shutdown(); // 쓰레드풀 종료 gracefully

        // then
        long 총_예약_횟수 = bookingRepository.count();

        assertThat(총_예약_횟수).isEqualTo(250);
    }

    private void 비동기_요청(BookingCreateRequest httpBody) {
        threadPool.execute(() -> {
            post("/bookings", httpBody, 일반_유저_토큰);
            latch.countDown();
        });
    }
}
