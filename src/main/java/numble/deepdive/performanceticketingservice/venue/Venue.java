package numble.deepdive.performanceticketingservice.venue;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.performance.Performance;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"seats"})
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int capacity;

    private String venuesType; // TOOO Enum화

    private String possibleTimes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Performance> performances = new ArrayList<>();

    public Venue(String name, int capacity, String venuesType, String possibleTimes, List<Seat> seats) {
        this.name = name;
        this.capacity = capacity;
        this.venuesType = venuesType;
        this.possibleTimes = possibleTimes;
        this.seats = seats;
    }
}
