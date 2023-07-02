package numble.deepdive.performanceticketingservice.performance.infrastructure;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCapacityStatResponses;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceMoneyStatResponses;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceVenueDto;
import numble.deepdive.performanceticketingservice.performance.dto.QPerformanceVenueDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static numble.deepdive.performanceticketingservice.performance.domain.QPerformance.performance;
import static numble.deepdive.performanceticketingservice.performance.domain.QPerformanceSeat.performanceSeat;
import static numble.deepdive.performanceticketingservice.venue.domain.QVenue.venue;

@Component
@RequiredArgsConstructor
public class PerformanceQuery {

    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;
    private final JPAQueryFactory queryFactory;

    public PerformanceCapacityStatResponses getStatByCapacity() {

        List<PerformanceVenueDto> list = findPerformanceVenueDtos();
        PerformanceSeat lastSeat = findTopByOrderByUpdatedAtDescOrThrow();

        return new PerformanceCapacityStatResponses(list, lastSeat);
    }


    public PerformanceMoneyStatResponses getStatByMoney() {

        List<Tuple> list = findByMoney();
        PerformanceSeat lastSeat = findTopByOrderByUpdatedAtDescOrThrow();

        return new PerformanceMoneyStatResponses(list, lastSeat);
    }


    private PerformanceSeat findTopByOrderByUpdatedAtDescOrThrow() {

        return performanceSeatRepository.findTopByOrderByUpdatedAtDescOrThrow();
    }

    private List<Tuple> findByMoney() {

        return queryFactory
                .select(performance.id,
                        performance.name,
                        performance.generalSeatPrice.sum().as("generalSeatPrice"),
                        performance.vipSeatPrice.sum(),
                        performance.generalSeatPrice.sum().add(performance.vipSeatPrice.sum()).as("total")
                )
                .from(performanceSeat)
                .innerJoin(performanceSeat.performance, performance)
                .groupBy(performance.id)
                .orderBy()
                .fetch();
    }

    private List<PerformanceVenueDto> findPerformanceVenueDtos() {

        return queryFactory
                .select(new QPerformanceVenueDto(performance, venue))
                .from(performance)
                .innerJoin(venue)
                .on(performance.venueId.eq(venue.id))
                .orderBy(performance.capacity.desc())
                .fetch();
    }
}
