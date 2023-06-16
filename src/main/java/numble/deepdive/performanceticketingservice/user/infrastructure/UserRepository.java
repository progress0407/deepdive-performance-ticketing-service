package numble.deepdive.performanceticketingservice.user.infrastructure;

import numble.deepdive.performanceticketingservice.user.domain.AbstractUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AbstractUser, Long> {

    public Optional<AbstractUser> findByEmail(String email);
}
