package numble.deepdive.performanceticketingservice.venue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Getter
public enum SeatType {

    GENERAL("일반", "일반 좌석"),
    VIP("VIP", "VIP 좌석");

    private final String name;
    private final String description;

    public static SeatType from(String seatTypeString) {

        return stream(values())
                .filter(type -> type.getName().equals(seatTypeString))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 좌석 타입입니다."));
    }
}
