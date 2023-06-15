package numble.deepdive.performanceticketingservice.venue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Seat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }
}
