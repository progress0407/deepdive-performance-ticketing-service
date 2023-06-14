package numble.deepdive.performanceticketingservice.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VenueCreateRequest {

    private String name;
    private int capacity;
    private String venuesType;
    private String possibleTimes;
    private List<SeatCreateRequest> seats = new ArrayList<>();

    public Venue toEntity() {

        List<Seat> seats = convertSeats();

        return new Venue(name, capacity, venuesType, possibleTimes, seats);
    }

    private List<Seat> convertSeats() {
        return seats.stream()
                .map(SeatCreateRequest::toEntity)
                .collect(toList());
    }
}
