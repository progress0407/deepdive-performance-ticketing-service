package numble.deepdive.performanceticketingservice.performance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {})
public class PerformanceSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String seatNumber;

    private String seatType; // TODO Enum으로 분리할것 VIP, 일반

    @JoinColumn(name = "performance_id")
    @ManyToOne
    private Performance performance;

    public PerformanceSeat(String seatNumber, String seatType, Performance performance) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.performance = performance;
    }
}
