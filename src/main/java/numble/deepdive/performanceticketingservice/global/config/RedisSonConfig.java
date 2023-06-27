package numble.deepdive.performanceticketingservice.global.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisSonConfig {

    private static final String REDISSON_HOST_PREFIX = "redis://";

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


    @Bean
    public RedissonClient redissonClient() {

        Config config = createConfig();

        return Redisson.create(config);
    }

    private Config createConfig() {

        Config config = new Config();
        String address = formatAddress();
        config.useSingleServer().setAddress(address);

        return config;
    }

    /**
     * ex)
     * <br>
     * redis://localhost:6379
     */
    private String formatAddress() {
        return REDISSON_HOST_PREFIX + host + ":" + port;
    }

}
