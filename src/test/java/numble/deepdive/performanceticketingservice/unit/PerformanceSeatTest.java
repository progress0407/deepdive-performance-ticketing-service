package numble.deepdive.performanceticketingservice.unit;

import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceSeatTest {

    @Test
    void 공연_좌석의_가격을_계산한다() {
        // given
        Set<PerformanceSeat> 공연_좌석들 = 공연_좌석들_생성();

        // when
        long 공연_좌석들의_가격_합 = 공연_좌석들.stream()
                .mapToLong(PerformanceSeat::calculatePriceAndGet)
                .sum();

        // then
        assertThat(공연_좌석들의_가격_합).isEqualTo(10_000 + 20_000);
    }

    private static Set<PerformanceSeat> 공연_좌석들_생성() {

        Performance 공연 = new Performance(1L, "이무진 공연", LocalDate.now(), LocalTime.now(), LocalTime.now().plusHours(2), 10_000, 20_000);
        VenueSeat 좌석1 = new VenueSeat("A1", "VIP");
        VenueSeat 좌석2 = new VenueSeat("B1", "GENERAL");
        공연.registerSeats(Set.of(좌석1, 좌석2));

        return 공연.getSeats();
    }

}