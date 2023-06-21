package numble.deepdive.performanceticketingservice.venue.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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
    private List<VenueSeat> seats = new ArrayList<>();

    public Venue(String name, List<VenueSeat> seats) {
        this.name = name;
        this.seats = seats;
    }
}
