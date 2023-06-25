package numble.deepdive.performanceticketingservice.performance.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.domain.VenueSeat;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final VenueRepository venueRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public void createPerformance(long venueId, Performance performance, UserCache userCache) {

        checkUserAuthorization(userCache);

        Venue venue = venueRepository.findAggregateByIdOrThrow(venueId);
        Set<VenueSeat> seats = venue.getSeats();

        performance.registerSeats(seats);

        performanceRepository.save(performance);
    }

    private static void checkUserAuthorization(UserCache userCache) {

        if (userCache.isGeneralUser()) {
            throw new BadRequestException("일반 사용자는 공연을 등록할 수 없습니다.");
        }
    }
}
