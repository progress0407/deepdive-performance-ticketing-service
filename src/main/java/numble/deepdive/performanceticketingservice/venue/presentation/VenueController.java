package numble.deepdive.performanceticketingservice.venue.presentation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.application.VenueService;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateRequest;
import numble.deepdive.performanceticketingservice.venue.dto.VenueCreateResponse;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequestMapping("/venues")
@RestController
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;
    private final VenueRepository venueRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueCreateResponse createVenue(@Valid @RequestBody VenueCreateRequest request,
                                           User user) {

        Venue entity = request.toEntity();
        long savedId = venueService.createVenue(entity, user);

        return new VenueCreateResponse(savedId);
    }

    @GetMapping
    public VenueListResponses findAllVenues() {

        List<Venue> venues = venueRepository.findAll();

        List<VenueListResponse> venueCollections = venues.stream()
                .map(VenueListResponse::new)
                .collect(toList());

        return new VenueListResponses(venueCollections);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class VenueListResponse {

        private long id;
        private String name;
        private int seatCount;

        public VenueListResponse(Venue venue) {
            this.id = venue.getId();
            this.name = venue.getName();
            this.seatCount = venue.getSeats().size();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class VenueListResponses {

        private List<VenueListResponse> venues;
    }
}
