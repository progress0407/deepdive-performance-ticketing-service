package numble.deepdive.performanceticketingservice.etc;

import jakarta.persistence.*;
import numble.deepdive.performanceticketingservice.venue.Venue;

import java.time.LocalDate;
import java.util.Map;

@Entity
public class Performance {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Venue venue;

    private String name;
    private int capacity;
    private LocalDate date;
    private String startTime;
    private String endTime;

    @ElementCollection
    private Map<String, Integer> prices;
}
