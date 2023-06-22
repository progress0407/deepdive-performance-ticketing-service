package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceListResponse;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceListResponses;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Transactional
public class PerformanceController {

    private final VenueRepository venueRepository;
    private final PerformanceRepository performanceRepository;

    @PostMapping("/performances")
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCreateResponse registerPerformance(@Valid @RequestBody PerformanceCreateRequest request,
                                                         User user) {

        if (user instanceof GeneralUser) {
            throw new BadRequestException("일반 사용자는 공연장을 등록할 수 없습니다.");
        }

        long venueId = request.getVenueId();
        Venue venue = venueRepository.findAggregateByIdOrThrow(venueId);
        Set<VenueSeat> seats = venue.getSeats();

        Performance performance = request.toEntity(venueId);
        performance.registerSeats(seats);

        performanceRepository.save(performance);

        return new PerformanceCreateResponse(performance.getId());
    }

    @GetMapping("/performances")
    public PerformanceListResponses findAllPerformances() {

        List<Performance> performances = performanceRepository.findAll();

        List<PerformanceListResponse> performanceCollections = performances.stream()
                .map(PerformanceListResponse::new)
                .collect(toList());

        return new PerformanceListResponses(performanceCollections);
    }

}
