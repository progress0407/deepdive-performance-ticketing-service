package numble.deepdive.performanceticketingservice.venue.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(exclude = {"seats"})
public class Venue extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VenueSeat> seats = new HashSet<>();

    public Venue(String name, Set<VenueSeat> seats) {
        this.name = name;
        this.seats = seats;
        for (VenueSeat seat : seats) {
            seat.mapVenue(this);
        }
    }
}
