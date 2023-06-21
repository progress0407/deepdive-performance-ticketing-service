package numble.deepdive.performanceticketingservice.venue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SeatCreateRequest {

    @NotBlank
    private String seatNumber;

    @NotBlank
    private String seatType;

    public VenueSeat toEntity() {

        return new VenueSeat(seatNumber, seatType);
    }
}
