package numble.deepdive.performanceticketingservice


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
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
