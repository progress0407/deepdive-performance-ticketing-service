package numble.deepdive.performanceticketingservice.venue.infrastructure;

import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    @EntityGraph(attributePaths = {"seats"})
    Optional<Venue> findAggregateById(long venueId);

    default Venue findAggregateByIdOrThrow(long venueId) {
        return findAggregateById(venueId).orElseThrow(() -> new BadRequestException("존재하지 않는 공연장입니다."));
    }
}
