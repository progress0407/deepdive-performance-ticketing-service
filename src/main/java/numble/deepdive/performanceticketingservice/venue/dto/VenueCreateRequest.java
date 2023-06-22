package numble.deepdive.performanceticketingservice.venue.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class VenueCreateRequest {

    @NotBlank
    private String name;

    @Valid
    @NotEmpty
    private Set<SeatCreateRequest> seats = new HashSet<>();

    public Venue toEntity() {

        Set<VenueSeat> seats = convertSeats();

        return new Venue(name, seats);
    }

    private Set<VenueSeat> convertSeats() {
        return seats.stream()
                .map(seatCreateRequest -> seatCreateRequest.toEntity())
                .collect(toSet());
    }
}
