package numble.deepdive.performanceticketingservice.venue.presentation;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import numble.deepdive.performanceticketingservice.venue.application.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/venues")
@RestController
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueCreateResponse createVenue(@RequestBody VenueCreateRequest request, @RequestHeader("Authorization") String token) {

        // TODO 사용자 토큰 확인. Here we should also validate the user's token.
        Venue entity = request.toEntity();
        long savedId = venueService.createVenue(entity);

        return new VenueCreateResponse(savedId);
    }
}
