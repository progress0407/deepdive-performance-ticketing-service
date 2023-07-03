package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.application.PerformanceService;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.dto.*;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceQuery;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceSeatRepository;
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
    private final PerformanceSeatRepository performanceSeatRepository;
    private final PerformanceQuery performanceQuery;
//    private final PerformanceSeatMapper performanceSeatMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCreateResponse registerPerformance(@Valid @RequestBody PerformanceCreateRequest request, UserCache userCache) {

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

    @GetMapping("/seats")
    public PerformanceSeatListResponses findAllPerformanceSeats() {

        List<PerformanceSeat> entities = performanceSeatRepository.findAll();
        List<PerformanceSeatListResponse> dtos = entities.stream()
                .map(PerformanceSeatMapper.INSTANCE::toDtoFromEntity)
                .toList();

        return new PerformanceSeatListResponses(dtos);
    }

    @GetMapping(value = "/stat/by-capacity")
    public PerformanceCapacityStatResponses getStatByCapacity() {

        return performanceQuery.getStatByCapacity();
    }

    @GetMapping("/stat/by-money")
    public PerformanceMoneyStatResponses getStatByMoney() {

        return performanceQuery.getStatByMoney();
    }

    private static List<PerformanceListResponse> convertDtoFromEntity(List<Performance> performances) {

        return performances.stream()
                .map(PerformanceListResponse::new)
                .toList();
    }
}
