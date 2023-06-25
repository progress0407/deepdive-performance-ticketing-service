package numble.deepdive.performanceticketingservice.concurrency;

import numble.deepdive.performanceticketingservice.acceptance.AcceptanceTest;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceSeatRepository;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {
                "classpath:sql/concurrency-others.sql",
                "classpath:sql/concurrency-venue-seat.sql",
                "classpath:sql/concurrency-performance-seat.sql"
        }
)
public class ConcurrencyTest extends AcceptanceTest {

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    PerformanceSeatRepository performanceSeatRepository;


    @Disabled
    @Test
    void todo() {
        Venue venue = venueRepository.findAggregateById(10_001).get();

        List<PerformanceSeat> performanceSeats = performanceSeatRepository.findAll();

        System.out.println("venue = " + venue);
        System.out.println("performanceSeats = " + performanceSeats);
    }
}
