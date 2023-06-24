package numble.deepdive.performanceticketingservice.user.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCacheRepository {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public void saveUserCache(String userEmail, UserCache userCache) {

        String serializedString = serialize(userCache);
        redisTemplate.opsForValue().set(userEmail, serializedString);
    }

    public UserCache findUser(String userEmail) {

        String foundCache = redisTemplate.opsForValue().get(userEmail);

        if (foundCache == null) {

            UserCache userCache = findFromDbAndConvert(userEmail);
            saveUserCache(userEmail, userCache);

            return userCache;
        }

        UserCache userCache = deserialize(foundCache);

        return userCache;
    }

    private UserCache findFromDbAndConvert(String userEmail) {
        User user = userRepository.findByEmailOrThrow(userEmail);
        UserCache userCache = new UserCache(user);
        return userCache;
    }

    private String serialize(UserCache value) {

        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private UserCache deserialize(String foundCache) {

        try {
            return objectMapper.readValue(foundCache, UserCache.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
