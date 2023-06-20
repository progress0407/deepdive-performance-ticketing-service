package numble.deepdive.performanceticketingservice.user.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public long registerUser(User user) {

        user.encodePassword(passwordEncoder);
        userRepository.save(user);

        return user.getId();
    }
}
