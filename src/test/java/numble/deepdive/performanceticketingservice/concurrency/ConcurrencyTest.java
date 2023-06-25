package numble.deepdive.performanceticketingservice.concurrency;

import numble.deepdive.performanceticketingservice.acceptance.AcceptanceTest;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/concurrency.sql")
public class ConcurrencyTest extends AcceptanceTest {

    @Autowired
    VenueRepository venueRepository;

    @Test
    void a() {
        List<Venue> all = venueRepository.findAll();

        System.out.println("all = " + all);
    }
}
