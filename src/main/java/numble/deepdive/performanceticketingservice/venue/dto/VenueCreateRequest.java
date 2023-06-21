package numble.deepdive.performanceticketingservice.venue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class VenueCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private List<SeatCreateRequest> seats = new ArrayList<>();

    public Venue toEntity() {

        List<VenueSeat> seats = convertSeats();

        return new Venue(name, seats);
    }

    private List<VenueSeat> convertSeats() {
        return seats.stream()
                .map(SeatCreateRequest::toEntity)
                .collect(toList());
    }
}
