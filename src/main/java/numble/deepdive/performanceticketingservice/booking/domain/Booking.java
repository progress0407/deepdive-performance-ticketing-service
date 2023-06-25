package numble.deepdive.performanceticketingservice.booking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private long totalPrice;

    @Embedded
    @Column(nullable = false)
    private PaymentInfo paymentInfo;

    public Booking(long totalPrice, PaymentInfo paymentInfo) {
        this.totalPrice = totalPrice;
        this.paymentInfo = paymentInfo;
    }
}
