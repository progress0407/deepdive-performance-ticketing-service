package numble.deepdive.performanceticketingservice.performance.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long venueId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private int generalSeatPrice;

    @Column(nullable = false)
    private int vipSeatPrice;

    @OneToMany(mappedBy = "performance", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PerformanceSeat> seats = new HashSet<>();

    public Performance(long venueId,
                       String name,
                       LocalDate date,
                       LocalTime startTime,
                       LocalTime endTime,
                       int generalSeatPrice,
                       int vipSeatPrice) {
        this.venueId = venueId;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.generalSeatPrice = generalSeatPrice;
        this.vipSeatPrice = vipSeatPrice;
    }

    public void registerSeats(Set<VenueSeat> seats) {
        this.seats = convertAndMapParentRelation(seats);
        this.capacity = seats.size();
    }

    /**
     * Type을 변경하고
     * <br>
     * 부모와의 연관관계를 매핑합니다
     */
    private Set<PerformanceSeat> convertAndMapParentRelation(Set<VenueSeat> seats) {

        return seats.stream()
                .map(seat -> new PerformanceSeat(seat, this))
                .collect(toSet());
    }
}
