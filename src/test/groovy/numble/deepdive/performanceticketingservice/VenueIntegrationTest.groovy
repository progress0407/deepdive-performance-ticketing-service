package numble.deepdive.performanceticketingservice

import com.fasterxml.jackson.databind.ObjectMapper
import numble.deepdive.performanceticketingservice.venue.SeatCreateRequest
import numble.deepdive.performanceticketingservice.venue.VenueCreateRequest
import numble.deepdive.performanceticketingservice.venue.VenueRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

//@SpringBootTest
@AutoConfigureMockMvc
@WebMvcTest
class VenueIntegrationTest extends Specification {

//    @Autowired
//    ObjectMapper objectMapper;

//    @Autowired
//    ApplicationContext context;

//    @Autowired
//    VenueRepository venueRepository;

    @Autowired
    private MockMvc mvc

    def "POST /venues creates a new venue"() {
        given: "A new venue payload"
        println "context: ${context}"
        expect:
        1 == 1
    }
}
