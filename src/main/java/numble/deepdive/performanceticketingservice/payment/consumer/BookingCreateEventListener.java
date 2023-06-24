package numble.deepdive.performanceticketingservice.payment.consumer;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.booking.dto.BookingCreateEvent;
import numble.deepdive.performanceticketingservice.payment.application.PaymentService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCreateEventListener implements ApplicationListener<BookingCreateEvent> {

    private final PaymentService paymentService;

    @Override
    public void onApplicationEvent(BookingCreateEvent event) {

        paymentService.pay(
                event.getPaymentMethod(),
                event.getCardNumber(),
                event.getCardExpiration(),
                event.getCardCVV(),
                event.getTotalPrice());
    }
}
