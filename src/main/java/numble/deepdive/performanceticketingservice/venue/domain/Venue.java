package numble.deepdive.performanceticketingservice.venue.domain;

import jakarta.persistence.*;
import lombok.*;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

import java.util.*;

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
    }
}
