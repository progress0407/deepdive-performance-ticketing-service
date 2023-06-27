package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.application.PerformanceService;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceListResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceListResponses;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;
    private final PerformanceRepository performanceRepository;

    @PostMapping("/performances")
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCreateResponse registerPerformance(@Valid @RequestBody PerformanceCreateRequest request,
                                                         UserCache userCache) {

        long venueId = request.venueId();
        Performance performance = request.toEntity(venueId);
        performanceService.createPerformance(venueId, performance, userCache);

        return new PerformanceCreateResponse(performance.getId());
    }

    @GetMapping("/performances")
    public PerformanceListResponses findAllPerformances() {

        List<Performance> performances = performanceRepository.findAll();

        List<PerformanceListResponse> performanceCollections = performances.stream()
                .map(PerformanceListResponse::new)
                .toList();

        return new PerformanceListResponses(performanceCollections);
    }

}
