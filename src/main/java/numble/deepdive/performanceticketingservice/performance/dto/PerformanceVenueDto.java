package numble.deepdive.performanceticketingservice.performance.dto;

import com.querydsl.core.annotations.QueryProjection;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;

public record PerformanceVenueDto(
        long performanceId,
        String performanceName,
        String venueName,
        int capacity
) {

    @QueryProjection
    public PerformanceVenueDto(Performance performance, Venue venue) {
        this(performance.getId(), performance.getName(), venue.getName(), performance.getCapacity());
    }
}
