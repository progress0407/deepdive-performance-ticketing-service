package numble.deepdive.performanceticketingservice.venue.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.user.domain.GeneralUser;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.infrastructure.VenueRepository;
import numble.deepdive.performanceticketingservice.venue.domain.Venue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepo;

    @Transactional
    public long createVenue(Venue venue, User user) {

        if (user instanceof GeneralUser) {
            throw new BadRequestException("일반 사용자는 공연장을 등록할 수 없습니다.");
        }

        venueRepo.save(venue);

        return venue.getId();
    }
}
