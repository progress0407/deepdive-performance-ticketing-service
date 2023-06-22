package numble.deepdive.performanceticketingservice.booking.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class PaymentInfo {

    private String paymentMethod;
    private String cardNumber;
    private String cardExpiration;
    private int cardCVV;
}
