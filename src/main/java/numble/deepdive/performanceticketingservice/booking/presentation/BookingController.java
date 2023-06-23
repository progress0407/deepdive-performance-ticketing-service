package numble.deepdive.performanceticketingservice.booking.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.application.BookingService;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateRequest;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateResponse;
import numble.deepdive.performanceticketingservice.booking.dto.PaymentInfoCreateRequest;
import numble.deepdive.performanceticketingservice.booking.infrastructure.BookingRepository;
import numble.deepdive.performanceticketingservice.user.domain.User;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;
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
    private final BookingRepository bookingRepository;

    @PostMapping("/bookings")
    @ResponseStatus(CREATED)
    public BookingCreateResponse bookPerformance(@Valid @RequestBody BookingCreateRequest request, User user) {

        long performanceId = request.getPerformanceId();
        long totalPriceRequest = request.getTotalPrice();
        PaymentInfo paymentInfo = convertPaymentInfo(request);
        List<String> seatNumbers = extractSeatNumbers(request);

        long bookedId = bookingService.bookPerformance(performanceId, paymentInfo, totalPriceRequest, seatNumbers, user);

        return new BookingCreateResponse(bookedId);
    }

    private static PaymentInfo convertPaymentInfo(BookingCreateRequest request) {

        PaymentInfoCreateRequest dto = request.getPaymentInfo();

        return new PaymentInfo(dto.getPaymentMethod(), dto.getCardNumber(), dto.getCardExpiration(), dto.getCardCVV());
    }

    private static List<String> extractSeatNumbers(BookingCreateRequest request) {

        return request.getSeats().stream()
                .map(SeatCreateRequest::getSeatNumber)
                .toList();
    }
}
