package numble.deepdive.performanceticketingservice.performance.dto;

import numble.deepdive.performanceticketingservice.performance.domain.Performance;

import java.time.LocalDate;
import java.time.LocalTime;

public record PerformanceListResponse(
        long id,
        String name,
        int seatCount,
        int capacity,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        int generalSeatPrice,
        int businessSeatPrice
) {

    public PerformanceListResponse(Performance performance) {
        this(
                performance.getId(),
                performance.getName(),
                performance.getSeats().size(),
                performance.getCapacity(),
                performance.getDate(),
                performance.getStartTime(),
                performance.getEndTime(),
                performance.getGeneralSeatPrice(),
                performance.getVipSeatPrice()
        );
    }
}

