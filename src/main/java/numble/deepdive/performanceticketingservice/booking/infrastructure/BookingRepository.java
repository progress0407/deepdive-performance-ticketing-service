package numble.deepdive.performanceticketingservice.booking.infrastructure;

import numble.deepdive.performanceticketingservice.booking.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
