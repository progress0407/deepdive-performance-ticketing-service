package numble.deepdive.performanceticketingservice.booking.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

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

    // TODO 필요 없다는 생각이 이름, 확정되면 삭제하자!
//    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BookingSeat> seats = new ArrayList<>();

    public Booking(long totalPrice, PaymentInfo paymentInfo) {
        this.totalPrice = totalPrice;
        this.paymentInfo = paymentInfo;
    }
}
