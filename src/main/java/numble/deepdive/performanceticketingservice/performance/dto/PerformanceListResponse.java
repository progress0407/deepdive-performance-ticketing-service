package numble.deepdive.performanceticketingservice.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerformanceListResponse {

    private long id;
    private String name;
    private int seatCount;
    private int capacity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int generalSeatPrice;
    private int businessSeatPrice;

    public PerformanceListResponse(Performance performance) {
        this.id = performance.getId();
        this.name = performance.getName();
        this.seatCount = performance.getSeats().size();
        this.capacity = performance.getCapacity();
        this.date = performance.getDate();
        this.startTime = performance.getStartTime();
        this.endTime = performance.getEndTime();
        this.generalSeatPrice = performance.getGeneralSeatPrice();
        this.businessSeatPrice = performance.getVipSeatPrice();
    }
}

