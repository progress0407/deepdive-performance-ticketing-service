package numble.deepdive.performanceticketingservice.performance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.Seat;
import numble.deepdive.performanceticketingservice.venue.Venue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long venueId;

    private String name;

    private int capacity;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    @ElementCollection
    private Map<String, Integer> prices;

    @OneToMany
    private Set<PerformanceSeat> performanceSeats = new HashSet<>();

    public Performance(long venueId, String name, int capacity, LocalDate date, LocalTime startTime, LocalTime endTime, Map<String, Integer> prices) {
        this.venueId = venueId;
        this.name = name;
        this.capacity = capacity;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.prices = prices;
    }

    public void registerSeats(List<Seat> seats) {
        this.performanceSeats = convertAndMapParentRelation(seats);
    }

    /**
     * Type을 변경하고
     * <br>
     * 부모와의 연관관계를 매핑합니다
     */
    private Set<PerformanceSeat> convertAndMapParentRelation(List<Seat> seats) {
        return seats.stream()
                .map(seat -> new PerformanceSeat(seat, this))
                .collect(toSet());
    }
}
