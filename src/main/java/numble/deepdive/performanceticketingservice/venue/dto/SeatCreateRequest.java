package numble.deepdive.performanceticketingservice.venue.dto;

import jakarta.validation.constraints.NotBlank;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

public record SeatCreateRequest(

        @NotBlank
        String seatNumber,

        @NotBlank
        String seatType
) {

    public VenueSeat toEntity() {

        return new VenueSeat(seatNumber, seatType);
    }
}
