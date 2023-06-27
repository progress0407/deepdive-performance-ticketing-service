package numble.deepdive.performanceticketingservice.performance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
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

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus = BookingStatus.AVAILABLE;

//    낙관적 락
//    @Version
//    private long version;

    @JoinColumn(name = "performance_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Performance performance;

    public PerformanceSeat(VenueSeat seat, Performance performance) {
        this.seatNumber = seat.getSeatNumber();
        this.seatType = seat.getSeatType();
        this.performance = performance;
    }

    public long calculatePriceAndGet() {

        if (seatType == SeatType.GENERAL) {
            return performance.getGeneralSeatPrice();
        } else if (seatType == SeatType.VIP) {
            return performance.getVipSeatPrice();
        } else {
            throw new BadRequestException("존재하지 않는 좌석 타입입니다.");
        }
    }

    public boolean isBooked() {
        return BookingStatus.BOOKED == bookingStatus;
    }

    public void markBooked() {
        this.bookingStatus = BookingStatus.BOOKED;
    }
}
