package numble.deepdive.performanceticketingservice.booking.dto;

import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;

public record PaymentInfoCreateRequest(
        String paymentMethod,
        String cardNumber,
        String cardExpiration,
        int cardCVV
) {
    public PaymentInfo toEntity() {

        return new PaymentInfo(paymentMethod, cardNumber, cardExpiration, cardCVV);
    }
}
