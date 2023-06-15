package numble.deepdive.performanceticketingservice.venue.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepo;

    @Transactional
    public long createVenue(Venue venue) {

        venueRepo.save(venue);

        return venue.getId();
    }
}
