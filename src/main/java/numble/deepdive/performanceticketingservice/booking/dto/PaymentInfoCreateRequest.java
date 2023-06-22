package numble.deepdive.performanceticketingservice.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PaymentInfoCreateRequest {
    private String paymentMethod;
    private String cardNumber;
    private String cardExpiration;
    private int cardCVV;
}
