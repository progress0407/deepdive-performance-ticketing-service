package numble.deepdive.performanceticketingservice.performance.presentation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
        List<VenueSeat> seats = venue.getSeats();

        Performance performance = request.toEntity(venueId);
        performance.registerSeats(seats);

        performanceRepository.save(performance);

        return performance.getId();
    }

    @GetMapping("/performances")
    public PerformancesListResponses findAllPerformances() {

        List<Performance> performances = performanceRepository.findAll();

        List<PerformancesListResponse> performanceCollections = performances.stream()
                .map(PerformancesListResponse::new)
                .collect(toList());

        return new PerformancesListResponses(performanceCollections);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PerformancesListResponse {

        private long id;
        private String name;
        private int seatCount;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
        private int general_seat_price;
        private int business_seat_price;

        public PerformancesListResponse(Performance performance) {
            this.id = performance.getId();
            this.name = performance.getName();
            this.seatCount = performance.getSeats().size();
            this.date= performance.getDate();
            this.startTime = performance.getStartTime();
            this.endTime = performance.getEndTime();
            this.general_seat_price = performance.getGeneralSeatPrice();
            this.business_seat_price = performance.getBusinessSeatPrice();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class PerformancesListResponses {

        private List<PerformancesListResponse> performances;
    }
}
