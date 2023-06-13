package numble.deepdive.performanceticketingservice.venue;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString(exclude = {"seats"})
public class Venue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private int capacity;
    private String venuesType;
    private String possibleTimes;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    public Venue(String name, int capacity, String venuesType, String possibleTimes, List<Seat> seats) {
        this.name = name;
        this.capacity = capacity;
        this.venuesType = venuesType;
        this.possibleTimes = possibleTimes;
        this.seats = seats;
    }
}
