package numble.deepdive.performanceticketingservice.venue.domain;

import jakarta.persistence.*;
import lombok.*;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"venue"})
public class VenueSeat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @JoinColumn(name = "venue_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Venue venue;

    public VenueSeat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = SeatType.from(seatType);
    }
}
