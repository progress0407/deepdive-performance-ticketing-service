package numble.deepdive.performanceticketingservice.performance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PerformanceListResponses {

    private List<PerformanceListResponse> performances;
}
