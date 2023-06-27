package numble.deepdive.performanceticketingservice.global.config;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.application.BookingService;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BookServiceLockProxy {

    private final BookingService bookingService;
    private final RedissonClient redissonClient;

    public long bookPerformance(long performanceId, PaymentInfo paymentInfo, long totalPriceRequest, List<String> seatNumbers, UserCache userCache) {
        String key = "book-performance-lock";
        RLock rLock = redissonClient.getLock(key);

        try {
            rLock.tryLock(5000, 2000, TimeUnit.MILLISECONDS);
            return bookingService.bookPerformance(performanceId, paymentInfo, totalPriceRequest, seatNumbers, userCache);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
    }
}
