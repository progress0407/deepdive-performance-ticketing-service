package numble.deepdive.performanceticketingservice.performance.infrastructure;

import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Long> {

    //    @Lock(value = LockModeType.PESSIMISTIC_WRITE) // 비관적 락
    Set<PerformanceSeat> findAllByPerformanceIdAndSeatNumberIn(Long performanceId, Collection<String> seatNumber);

    Optional<PerformanceSeat> findTopByOrderByUpdatedAtDesc();

    default PerformanceSeat findTopByOrderByUpdatedAtDescOrThrow() {

        return findTopByOrderByUpdatedAtDesc().orElseThrow(() -> new BadRequestException("PerformanceSeat가 없습니다."));
    }
}
