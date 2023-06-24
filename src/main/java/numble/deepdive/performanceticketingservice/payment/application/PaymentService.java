package numble.deepdive.performanceticketingservice.payment.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PaymentService {

    @Transactional
    public void pay(String paymentMethod, String cardNumber, String cardExpiration, int cardCvv, long totalPrice) {

        delay(1_000);

        log.info("결제가 완료되었습니다. [결제 수단: {}, 카드 번호: {}, 카드 만기일: {}, CVV 번호: {}, 총 결제 가격: {}]",
                paymentMethod,
                cardNumber,
                cardExpiration,
                cardCvv,
                totalPrice);
    }

    private static void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
