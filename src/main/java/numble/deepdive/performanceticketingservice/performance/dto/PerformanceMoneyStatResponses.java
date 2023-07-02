package numble.deepdive.performanceticketingservice.performance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.Tuple;
import lombok.Getter;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PerformanceMoneyStatResponses {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PerformanceMoneyStatResponse> list = null;

    public PerformanceMoneyStatResponses(List<Tuple> list, PerformanceSeat lastSeat) {
        this.list = list.stream()
                .map(PerformanceMoneyStatResponse::new)
                .toList();
        this.updatedAt = updatedAt(lastSeat);
    }

    private LocalDateTime updatedAt(PerformanceSeat lastSeat) {
        if (lastSeat != null) {
            return lastSeat.getUpdatedAt();
        }
        return null;
    }
}
