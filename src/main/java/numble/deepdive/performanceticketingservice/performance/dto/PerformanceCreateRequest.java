package numble.deepdive.performanceticketingservice.performance.dto;

import jakarta.validation.constraints.NotNull;
import numble.deepdive.performanceticketingservice.performance.domain.Performance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public record PerformanceCreateRequest(

        @NotNull(message = "공연장은 필수입니다.")
        long venueId,

        @NotNull(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        String date,

        @NotNull(message = "시작시간은 필수입니다.")
        String startTime,

        @NotNull(message = "종료시간은 필수입니다.")
        String endTime,

        Map<String, Integer> gradeToPrice
) {

    public Performance toEntity(long venueId) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new Performance(
                venueId,
                name,
                LocalDate.parse(date, dateFormatter),
                LocalTime.parse(startTime, timeFormatter),
                LocalTime.parse(endTime, timeFormatter),
                gradeToPrice.get("일반"),
                gradeToPrice.get("VIP")
        );
    }
}
