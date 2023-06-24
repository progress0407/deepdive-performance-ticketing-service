package numble.deepdive.performanceticketingservice.booking.dto;

import lombok.Getter;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;
import org.springframework.context.ApplicationEvent;

@Getter
public class BookingCreateEvent extends ApplicationEvent {

    private final String paymentMethod;
    private final String cardNumber;
    private final String cardExpiration;
    private final int cardCVV;
    private final long totalPrice;

    public BookingCreateEvent(Object source, PaymentInfo paymentInfo, long totalPrice) {
        super(source);
        this.paymentMethod = paymentInfo.getPaymentMethod();
        this.cardNumber = paymentInfo.getCardNumber();
        this.cardExpiration = paymentInfo.getCardExpiration();
        this.cardCVV = paymentInfo.getCardCVV();
        this.totalPrice = totalPrice;
    }
}
