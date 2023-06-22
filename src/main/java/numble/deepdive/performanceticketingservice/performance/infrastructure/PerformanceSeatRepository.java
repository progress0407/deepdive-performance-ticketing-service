package numble.deepdive.performanceticketingservice.performance.infrastructure;

import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Long> {

    Set<PerformanceSeat> findAllByPerformanceIdAndSeatNumberIn(Long performanceId, Collection<String> seatNumber);
}
