package numble.deepdive.performanceticketingservice.venue.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepo;

    @Transactional
    public long createVenue(Venue venue, UserCache userCache) {

        checkUserAuthorization(userCache);

        venueRepo.save(venue);

        return venue.getId();
    }

    private static void checkUserAuthorization(UserCache userCache) {

        if (userCache.isGeneralUser()) {
            throw new BadRequestException("일반 사용자는 공연장을 등록할 수 없습니다.");
        }
    }
}
