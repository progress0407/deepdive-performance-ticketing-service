package numble.deepdive.performanceticketingservice.booking.dto;

import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import numble.deepdive.performanceticketingservice.venue.dto.SeatCreateRequest;

import java.util.List;

public record BookingCreateRequest(
        long performanceId,
        List<SeatCreateRequest> seats,
        long totalPrice,
        PaymentInfoCreateRequest paymentInfo) {

    public List<String> extractSeatNumbers() {

        return this.seats().stream()
                .map(SeatCreateRequest::seatNumber)
                .toList();
    }

    public PaymentInfo getPaymentInfoEntity() {

        return paymentInfo.toEntity();
    }
}

