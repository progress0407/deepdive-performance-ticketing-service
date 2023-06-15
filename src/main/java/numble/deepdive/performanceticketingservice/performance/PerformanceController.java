package numble.deepdive.performanceticketingservice.performance;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.venue.Seat;
import numble.deepdive.performanceticketingservice.venue.Venue;
import numble.deepdive.performanceticketingservice.venue.VenueRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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

    @NoArgsConstructor
    @Getter
    @ToString
    static class PerformanceCreateRequest {

        @NotNull(message = "공연장은 필수입니다.")
        private Long venueId;

        @NotNull(message = "이름은 필수입니다.")
        private String name;

        @NotNull(message = "수용 인원는 필수입니다.")
        private String capacity;

        @NotNull(message = "날짜는 필수입니다.")
        private String date;

        @NotNull(message = "시작시간은 필수입니다.")
        private String startTime;

        @NotNull(message = "종료시간은 필수입니다.")
        private String endTime;

        private Map<String, Integer> gradeToPrice;

        public Performance toEntity(long venueId) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return new Performance(venueId, name, Integer.valueOf(capacity), LocalDate.parse(date, dateFormatter), parse(startTime, timeFormatter), parse(endTime, timeFormatter), gradeToPrice);
        }
    }
}
