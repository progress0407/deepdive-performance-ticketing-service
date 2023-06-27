package numble.deepdive.performanceticketingservice.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.booking.application.BookingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisLockAopProcessor {

    private final BookingService bookingService;
    private final RedissonClient redissonClient;

    @Around("@annotation(redisLock)")
    public Object doLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {

        String key = redisLock.key();
        long waitTime = redisLock.waitTime();
        long leaseTime = redisLock.leaseTime();
        TimeUnit timeUnit = redisLock.timeUnit();

        RLock rLock = redissonClient.getLock(key);

        try {
            rLock.tryLock(waitTime, leaseTime, timeUnit);

            return joinPoint.proceed();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
    }

}
