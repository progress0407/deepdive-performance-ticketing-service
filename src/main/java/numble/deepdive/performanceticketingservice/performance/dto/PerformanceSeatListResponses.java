package numble.deepdive.performanceticketingservice.performance.dto;

import java.util.List;

public record PerformanceSeatListResponses(List<PerformanceSeatListResponse> list) {

    public PerformanceSeatListResponses {
    }

    @Override
    public List<PerformanceSeatListResponse> list() {
        return list;
    }
}
