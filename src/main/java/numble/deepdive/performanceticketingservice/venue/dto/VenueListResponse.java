package numble.deepdive.performanceticketingservice.venue.dto;

import numble.deepdive.performanceticketingservice.venue.domain.Venue;

public record VenueListResponse(long id, String name, int seatCount) {

    public VenueListResponse(Venue venue) {
        this(venue.getId(), venue.getName(), venue.getSeats().size());
    }
}

