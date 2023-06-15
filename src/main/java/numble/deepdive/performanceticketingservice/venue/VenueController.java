package numble.deepdive.performanceticketingservice.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/venues")
@RestController
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueCreateResponse createVenue(@RequestBody VenueCreateRequest request, @RequestHeader("X-Authorization") String xAuthorization) {

        // TODO 사용자 토큰 확인. Here we should also validate the user's token.
        Venue entity = request.toEntity();
        long savedId = venueService.createVenue(entity);

        return new VenueCreateResponse(savedId);
    }
}
