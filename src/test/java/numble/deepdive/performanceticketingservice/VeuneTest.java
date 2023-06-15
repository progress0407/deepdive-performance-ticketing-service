package numble.deepdive.performanceticketingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class VeuneTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ApplicationContext context;

    @Autowired
    VenueRepository venueRepository;

    @Test
    void a() {
        System.out.println("context = " + context);
        System.out.println("objectMapper = " + objectMapper);
        System.out.println("venueRepository = " + venueRepository);
    }
}
