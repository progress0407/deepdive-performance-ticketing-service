package numble.deepdive.performanceticketingservice.performance.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookingStatus {

    AVAILABLE("예약 가능"),
    BOOKED("예약 완료");

    private final String description;
}
