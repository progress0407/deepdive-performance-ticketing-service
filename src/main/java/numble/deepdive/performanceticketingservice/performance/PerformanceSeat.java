package numble.deepdive.performanceticketingservice.performance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.Seat;
import numble.deepdive.performanceticketingservice.venue.SeatType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {})
public class PerformanceSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String seatNumber;

    private SeatType seatType; // TODO Enum으로 분리할것 VIP, 일반

    @JoinColumn(name = "performance_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Performance performance;

    public PerformanceSeat(Seat seat, Performance performance) {
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.performance = performance;
    }
}
