package numble.deepdive.performanceticketingservice.booking.application;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.domain.Booking;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateEvent;
import numble.deepdive.performanceticketingservice.booking.infrastructure.BookingRepository;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceSeatRepository;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PerformanceSeatRepository performanceSeatRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public long bookPerformance(long performanceId, PaymentInfo paymentInfo, long totalPriceRequest, List<String> seatNumbers, UserCache userCache) {

        var performanceSeats = findPerformanceSeats(performanceId, seatNumbers);
        long realTotalPrice = calculateRealTotalPrice(performanceSeats);

        checkUserAuthorization(userCache);
        checkPriceSame(totalPriceRequest, realTotalPrice);
        checkAlreadyBookedSeats(performanceSeats);

        // 정상 흐름
        Booking booking = new Booking(realTotalPrice, paymentInfo);
        bookingRepository.save(booking);
        markBook(performanceSeats);

        eventPublisher.publishEvent(new BookingCreateEvent(this, paymentInfo, realTotalPrice));

        return booking.getId();
    }


    // 예약하고자 하는 좌석들 find
    private Set<PerformanceSeat> findPerformanceSeats(long performanceId, List<String> seatNumbers) {

        return performanceSeatRepository.findAllByPerformanceIdAndSeatNumberIn(performanceId, seatNumbers);
    }


    private static long calculateRealTotalPrice(Set<PerformanceSeat> performanceSeats) {

        return performanceSeats.stream()
                .mapToLong(PerformanceSeat::calculatePriceAndGet)
                .sum();
    }


    private static void checkUserAuthorization(UserCache userCache) {

        if (userCache.isBusinessUser()) {
            throw new BadRequestException("사업자는 예매를 할 수 없습니다.");
        }
    }


    /**
     * 가격 일치 여부 확인
     */
    private static void checkPriceSame(long totalPriceRequest, long realTotalPrice) {

        if (realTotalPrice != totalPriceRequest) {
            throw new BadRequestException("총 가격이 일치하지 않습니다.");
        }
    }


    /**
     * 이미 예약이 된 좌석인지 여부
     */
    private static void checkAlreadyBookedSeats(Set<PerformanceSeat> performanceSeats) {

        Set<PerformanceSeat> alreadyBookedSeats = filterAlreadyBookedSeats(performanceSeats);

        if (alreadyBookedSeats.size() > 0) {
            throw new BadRequestException("이미 예약된 좌석이 포함되어 있습니다.");
        }
    }


    private static Set<PerformanceSeat> filterAlreadyBookedSeats(Set<PerformanceSeat> performanceSeats) {

        return performanceSeats.stream()
                .filter(PerformanceSeat::isBooked)
                .collect(toSet());
    }


    private static void markBook(Set<PerformanceSeat> performanceSeats) {

        for (PerformanceSeat performanceSeat : performanceSeats) {
            performanceSeat.markBooked();
        }
    }
}
