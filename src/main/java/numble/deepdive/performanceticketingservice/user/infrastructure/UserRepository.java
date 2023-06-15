package numble.deepdive.performanceticketingservice.user.infrastructure;

import numble.deepdive.performanceticketingservice.user.domain.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {
}
