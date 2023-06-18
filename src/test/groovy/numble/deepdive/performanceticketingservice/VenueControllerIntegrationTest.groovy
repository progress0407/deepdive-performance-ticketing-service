/*
package numble.deepdive.performanceticketingservice

import com.fasterxml.jackson.databind.ObjectMapper
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@SpringBootTest
//@AutoConfigureMockMvc
class VenueControllerIntegrationTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Autowired
    private ObjectMapper objectMapper

    @Autowired
    private ApplicationContext context

    def "POST /venues creates a new venue"() {
        given: "A new venue payload"
        List<SeatCreateRequest> seats = [
                new SeatCreateRequest("A1", "VIP"),
                new SeatCreateRequest("B1", "일반")
        ]
        VenueCreateRequest venues = new VenueCreateRequest("공연장 이름", 1000, "FIXED-SEAT", "10:00-20:00", seats)
        String jsonRequest = objectMapper.writeValueAsString(venues)

        when: "Request is made to create a new venue"
        def result = mockMvc.perform(post("/venues")
                .header("X-Authorization", "hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andReturn()

        then: "Response status is CREATED"
        result.response.status == HttpStatus.CREATED.value()

        and: "Response contains the created venue"
        // TODO: Validate the response
    }
}
*/
