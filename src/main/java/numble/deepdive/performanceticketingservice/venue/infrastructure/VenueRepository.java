package numble.deepdive.performanceticketingservice.venue.infrastructure;

import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}
