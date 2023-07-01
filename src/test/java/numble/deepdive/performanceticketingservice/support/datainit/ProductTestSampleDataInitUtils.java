package numble.deepdive.performanceticketingservice.support.datainit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.sample.application.TestSampleDataInitController;
import numble.deepdive.performanceticketingservice.sample.presentation.TestSampleDataInitService;

import static java.lang.System.out;

/**
 * Product환경에서 데이터를 생성하기 위한 클래스입니다.
 * <br>
 * 사용법은 main 함수 자체적으로 데이터를 생성합니다.
 * <br>
 * 생성된 파일은 src/test/resources/sql/ 하위에 위치합니다.
 * <br>
 *
 * @see TestSampleDataInitController
 * @see TestSampleDataInitService
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductTestSampleDataInitUtils extends AbstractDataInitUtils {

    public static void main(String[] args) {

//        print_venue_seat(20001, 20001, 200, 800);
//        print_venue_seat(20002, 22001, 40, 160);

        print_performance_seat(20001, 20001, 180, 720); // 아이유
        print_performance_seat(20002, 22001, 80, 720); // 이무진
        print_performance_seat(20003, 24001, 40, 160); // BTS
    }

    private static void print_venue_seat(int venueId, int startSeatPkNumber, int vipSeatNumber, int generalSeatNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();
        sb.append(INSERT_INTO_VENUE_SEAT_PREVIOUS_CLAUSE);

        for (int i = 1; i <= vipSeatNumber; i++) {
            String aValue = formatVenueSeatValue(venueId, "VIP", i, startSeatPkNumber);
            sb.append(aValue);
            sb.append(", \n");
        }

        int endSeatNumber = vipSeatNumber + generalSeatNumber;
        for (int i = vipSeatNumber + 1; i <= endSeatNumber; i++) {
            String aValue = formatVenueSeatValue(venueId, "GENERAL", i, startSeatPkNumber);
            sb.append(aValue);
            appendEndOfStatement(i, endSeatNumber, sb);
        }

        String result = sb.toString();
        out.println(result);
        print_end();
    }

    private static void print_performance_seat(int performanceId, int startSeatPkNumber, int generalSeatNumber, int vipSeatNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();
        sb.append("insert into performance_seat(performance_id, id, seat_number, seat_type, booking_status, created_at, updated_at)\nvalues ");

        for (int i = 1; i <= vipSeatNumber; i++) {
            String aValue = formatPerformanceSeatValue(performanceId, "VIP", startSeatPkNumber, i);
            sb.append(aValue);
            sb.append(", \n");
        }

        int endSeatNumber = vipSeatNumber + generalSeatNumber;
        for (int i = vipSeatNumber + 1; i <= endSeatNumber; i++) {
            String aValue = formatPerformanceSeatValue(performanceId, "GENERAL", startSeatPkNumber, i);
            sb.append(aValue);
            appendEndOfStatement(i, endSeatNumber, sb);
        }

        out.println(sb);
        print_end();
    }
}
