package numble.deepdive.performanceticketingservice.performance.infrastructure;

import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
}
