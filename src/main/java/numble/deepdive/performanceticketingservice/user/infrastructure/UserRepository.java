package numble.deepdive.performanceticketingservice.user.infrastructure;

import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.auth.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
