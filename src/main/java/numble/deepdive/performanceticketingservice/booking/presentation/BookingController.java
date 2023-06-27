package numble.deepdive.performanceticketingservice.booking.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.application.BookingService;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateResponse;
import numble.deepdive.performanceticketingservice.booking.infrastructure.BookingRepository;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    //    private final BookServiceLockProxy bookServiceLockProxy;
    private final BookingRepository bookingRepository;

    @PostMapping("/bookings")
    @ResponseStatus(CREATED)
    public BookingCreateResponse bookPerformance(@Valid @RequestBody BookingCreateRequest request, UserCache userCache) {

        long performanceId = request.performanceId();
        long totalPriceRequest = request.totalPrice();
        PaymentInfo paymentInfo = request.getPaymentInfoEntity();
        List<String> seatNumbers = request.extractSeatNumbers();

        long bookedId = bookingService.bookPerformance(performanceId, paymentInfo, totalPriceRequest, seatNumbers, userCache);
//        long bookedId = bookServiceLockProxy.bookPerformance(performanceId, paymentInfo, totalPriceRequest, seatNumbers, userCache);

        return new BookingCreateResponse(bookedId);
    }
}
