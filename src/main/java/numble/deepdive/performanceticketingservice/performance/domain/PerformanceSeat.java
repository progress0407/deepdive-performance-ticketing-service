package numble.deepdive.performanceticketingservice.performance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;
import numble.deepdive.performanceticketingservice.venue.domain.SeatType;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"performance"})
public class PerformanceSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String seatNumber;

    private SeatType seatType;

    @JoinColumn(name = "performance_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Performance performance;

    public PerformanceSeat(VenueSeat seat, Performance performance) {
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.performance = performance;
    }
}
