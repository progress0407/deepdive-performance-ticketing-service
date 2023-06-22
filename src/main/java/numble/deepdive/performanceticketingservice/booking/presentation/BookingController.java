package numble.deepdive.performanceticketingservice.booking.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.domain.Booking;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateResponse;
import numble.deepdive.performanceticketingservice.booking.dto.PaymentInfoCreateRequest;
import numble.deepdive.performanceticketingservice.booking.infrastructure.BookingRepository;
import numble.deepdive.performanceticketingservice.global.exception.BadRequestException;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;
import numble.deepdive.performanceticketingservice.performance.domain.PerformanceSeat;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceRepository;
import numble.deepdive.performanceticketingservice.performance.infrastructure.PerformanceSeatRepository;
import numble.deepdive.performanceticketingservice.user.domain.BusinessUser;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@Transactional
public class BookingController {

    private final PerformanceRepository performanceRepository;
    private final BookingRepository bookingRepository;
    private final PerformanceSeatRepository performanceSeatRepository;

    @PostMapping("/bookings")
    public BookingCreateResponse bookPerformance(@Valid @RequestBody BookingCreateRequest request, User user) {

        if (user instanceof BusinessUser) {
            throw new BadRequestException("사업자는 예매를 할 수 없습니다.");
        }

        // 예매가
        int totalPriceRequest = request.getTotalPrice();

        // 공연 ID
        long performanceId = request.getPerformanceId();
        Performance performance = performanceRepository.findById(performanceId).orElseThrow(() -> new BadRequestException("존재하지 않는 공연입니다."));

        // 공연 좌석 ID들
        List<String> seatNumbers = request.getSeats().stream()
                .map(SeatCreateRequest::getSeatNumber)
                .collect(toList());

        // 어떻게 가져올 것인가!!
        // TODO 쿼리 잘 생성되는지 확인
        List<PerformanceSeat> performanceSeats = performanceSeatRepository.findAllByPerformanceIdAndSeatNumberIn(performanceId, seatNumbers);
        long realTotalPrice = performanceSeats.stream()
                .mapToLong(PerformanceSeat::calculatePriceAndGet)
                .sum();

        // 가격 일치 여부
        if (realTotalPrice != totalPriceRequest) {
            throw new BadRequestException("총 가격이 일치하지 않습니다.");
        }

        // 이미 예약이 된 좌석인지 여부
        List<PerformanceSeat> alreadyBookedSeats = performanceSeats.stream()
                .filter(PerformanceSeat::isBooked)
                .toList();

        if (alreadyBookedSeats.size() > 0) {
            throw new BadRequestException("이미 예약된 좌석이 포함되어 있습니다.");
        }

        /**
         * 정상 처리
         */
        PaymentInfo paymentInfo = convertPaymentInfo(request);

        Booking booking = new Booking(realTotalPrice, paymentInfo);

        bookingRepository.save(booking);

        return new BookingCreateResponse(booking.getId());
    }

    private static PaymentInfo convertPaymentInfo(BookingCreateRequest request) {

        PaymentInfoCreateRequest dto = request.getPaymentInfo();

        return new PaymentInfo(dto.getPaymentMethod(), dto.getCardNumber(), dto.getCardExpiration(), dto.getCardCVV());
    }
}
