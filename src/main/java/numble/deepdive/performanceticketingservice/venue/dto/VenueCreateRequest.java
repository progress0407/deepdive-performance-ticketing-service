package numble.deepdive.performanceticketingservice.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.domain.Seat;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VenueCreateRequest {

    private String name;
    private List<SeatCreateRequest> seats = new ArrayList<>();

    public Venue toEntity() {

        List<Seat> seats = convertSeats();

        return new Venue(name, seats);
    }

    private List<Seat> convertSeats() {
        return seats.stream()
                .map(SeatCreateRequest::toEntity)
                .collect(toList());
    }
}
