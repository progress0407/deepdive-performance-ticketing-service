package numble.deepdive.performanceticketingservice.performance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PerformanceCapacityStatResponses {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PerformanceVenueDto> list = null;

    public PerformanceCapacityStatResponses(List<PerformanceVenueDto> performanceVenueDtos, PerformanceSeat lastSeat) {
        this.list = performanceVenueDtos;
        this.updatedAt = updatedAt(lastSeat);
    }

    private LocalDateTime updatedAt(PerformanceSeat lastSeat) {
        if (lastSeat != null) {
            return lastSeat.getUpdatedAt();
        }
        return null;
    }
}
