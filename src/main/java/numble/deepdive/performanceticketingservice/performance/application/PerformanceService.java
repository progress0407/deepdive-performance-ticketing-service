package numble.deepdive.performanceticketingservice.performance.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.dto.PerformanceCreateRequest;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {

    private final VenueRepository venueRepository;
    private final PerformanceRepository performanceRepository;

    public void createPerformance(long venueId, Performance performance, User user) {

        if (user instanceof GeneralUser) {
            throw new BadRequestException("일반 사용자는 공연장을 등록할 수 없습니다.");
        }


        Venue venue = venueRepository.findAggregateByIdOrThrow(venueId);
        Set<VenueSeat> seats = venue.getSeats();

        performance.registerSeats(seats);

        performanceRepository.save(performance);
    }
}
