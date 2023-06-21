package numble.deepdive.performanceticketingservice.venue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SeatCreateRequest {

    private String seatNumber;
    private String seatType;

    public VenueSeat toEntity() {

        return new VenueSeat(seatNumber, seatType);
    }
}
