package numble.deepdive.performanceticketingservice.venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SeatCreateRequest {

    private String seatNumber;
    private String seatType;

    public Seat toEntity() {

        return new Seat(seatNumber, seatType);
    }
}
