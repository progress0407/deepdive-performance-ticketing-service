package numble.deepdive.performanceticketingservice.venue;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.ToString;

@Entity
@ToString
public class Seat {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String seatNumber;
    private String seatType;

    public Seat(String seatNumber, String seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }
}
