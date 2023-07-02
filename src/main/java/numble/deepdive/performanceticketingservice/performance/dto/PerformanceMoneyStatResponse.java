package numble.deepdive.performanceticketingservice.performance.dto;

import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;

import static numble.deepdive.performanceticketingservice.performance.domain.QPerformance.performance;

public record PerformanceMoneyStatResponse(
        long performanceId,
        String performanceName,
        int totalVipSeatPrice,
        int totalGeneralSeatPrice,
        int totalSeatPrice
) {

    @QueryProjection
    public PerformanceMoneyStatResponse(Tuple tuple) {
        this(tuple.get(performance.id),
                tuple.get(performance.name),
                tuple.get(2, Integer.class),
                tuple.get(3, Integer.class),
                tuple.get(4, Integer.class));
    }
}
