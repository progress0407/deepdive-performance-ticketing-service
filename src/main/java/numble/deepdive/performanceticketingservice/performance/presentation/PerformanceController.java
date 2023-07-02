package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.application.PerformanceService;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.dto.*;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceQuery;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/performances")
@RestController
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;
    private final PerformanceRepository performanceRepository;
    private final PerformanceQuery performanceQuery;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCreateResponse registerPerformance(@Valid @RequestBody PerformanceCreateRequest request,
                                                         UserCache userCache) {

        long venueId = request.venueId();
        Performance performance = request.toEntity(venueId);
        performanceService.createPerformance(venueId, performance, userCache);

        return new PerformanceCreateResponse(performance.getId());
    }

    @GetMapping
    public PerformanceListResponses findAllPerformances() {

        List<Performance> performances = performanceRepository.findAll();
        List<PerformanceListResponse> performanceCollections = convertDtoFromEntity(performances);

        return new PerformanceListResponses(performanceCollections);
    }

    @GetMapping(value = "/stat/by-capacity")
    public PerformanceCapacityStatResponses getStatByCapacity() {

        PerformanceCapacityStatResponses statByCapacity = performanceQuery.getStatByCapacity();

        return statByCapacity;
    }

    @GetMapping("/stat/by-money")
    public PerformanceMoneyStatResponses getStatByMoney() {

        PerformanceMoneyStatResponses response = performanceQuery.getStatByMoney();

        return response;
    }

    private static List<PerformanceListResponse> convertDtoFromEntity(List<Performance> performances) {

        return performances.stream()
                .map(PerformanceListResponse::new)
                .toList();
    }
}
