package numble.deepdive.performanceticketingservice.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.booking.domain.PaymentInfo;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class PaymentInfoCreateRequest {
    private String paymentMethod;
    private String cardNumber;
    private String cardExpiration;
    private int cardCVV;

    public PaymentInfo toEntity() {

        return new PaymentInfo(paymentMethod, cardNumber, cardExpiration, cardCVV);
    }
}
