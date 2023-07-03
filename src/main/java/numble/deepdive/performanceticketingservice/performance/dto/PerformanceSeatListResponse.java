package numble.deepdive.performanceticketingservice.performance.dto;

import lombok.*;
import numble.deepdive.performanceticketingservice.performance.domain.BookingStatus;
import numble.deepdive.performanceticketingservice.venue.domain.SeatType;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PerformanceSeatListResponse {

    private Long id;

    private String seatNumber;

    private SeatType seatType;

    private BookingStatus bookingStatus;
}
