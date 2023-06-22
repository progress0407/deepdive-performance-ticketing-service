package numble.deepdive.performanceticketingservice.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VenueListResponse {

    private long id;
    private String name;
    private int seatCount;

    public VenueListResponse(Venue venue) {
        this.id = venue.getId();
        this.name = venue.getName();
        this.seatCount = venue.getSeats().size();
    }
}
