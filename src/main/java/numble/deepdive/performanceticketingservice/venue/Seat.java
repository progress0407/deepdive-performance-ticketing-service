package numble.deepdive.performanceticketingservice.venue;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Seat {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String seatNumber;

    private String seatType; // TODO Enum으로 분리할것 VIP, 일반

    @JoinColumn(name = "venue_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

    public Seat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }
}
