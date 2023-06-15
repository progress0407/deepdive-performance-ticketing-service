package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.venue.domain.Seat;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.time.LocalTime.parse;

@RestController
@RequiredArgsConstructor
@Transactional
public class PerformanceController {

    private final VenueRepository venueRepository;
    private final PerformanceRepository performanceRepository;

    @PostMapping("/performances")
    public long registerPerformance(@Valid @RequestBody PerformanceCreateRequest request) {

        Long venueId = request.getVenueId();
        Venue venue = venueRepository.findById(venueId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공연장입니다."));
        List<Seat> seats = venue.getSeats();

        Performance performance = request.toEntity(venueId);
        performance.registerSeats(seats);

        performanceRepository.save(performance);

        return performance.getId();
    }
}
