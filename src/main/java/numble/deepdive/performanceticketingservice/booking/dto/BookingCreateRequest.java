package numble.deepdive.performanceticketingservice.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class BookingCreateRequest {

    private long performanceId;
    private List<SeatCreateRequest> seats;
    private int totalPrice;
    private PaymentInfoCreateRequest paymentInfo;
}
