package numble.deepdive.performanceticketingservice.venue;

import lombok.RequiredArgsConstructor;
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
