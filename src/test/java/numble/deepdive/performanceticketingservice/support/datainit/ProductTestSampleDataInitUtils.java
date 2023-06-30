package numble.deepdive.performanceticketingservice.support.datainit;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import numble.deepdive.performanceticketingservice.sample.TestSampleDataInitService;

import static java.lang.System.out;

/**
 * Product환경에서 데이터를 생성하기 위한 클래스입니다.
 * <br>
 * 사용법은 main 함수 자체적으로 데이터를 생성합니다.
 * <br>
 * 생성된 파일은 src/test/resources/sql/ 하위에 위치합니다.
 * <br>
 *
 * @see TestSampleDataInitService
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductTestSampleDataInitUtils extends AbstractDataInitUtils {

    public static void main(String[] args) {

        print_venue_seat(20001, 20001, 200, 800);
//        print_venue_seat(20002, 22001, 40, 160);

//        print_performance_seat(250);
//        print_performance_seat_with_opt_lock(250);
    }

    public static void print_venue_seat(int venueId, int startSeatNumber, int vipSeatNumber, int generalSeatNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();
        sb.append(INSERT_INTO_VENUE_SEAT_PREVIOUS_CLAUSE);

        for (int i = 1; i <= vipSeatNumber; i++) {
            String aValue = formatAValue(venueId, "VIP", i, startSeatNumber);
            sb.append(aValue);
            sb.append(", \n");
        }

        int endSeatNumber = vipSeatNumber + generalSeatNumber;

        for (int i = vipSeatNumber + 1; i <= endSeatNumber; i++) {
            String aValue = formatAValue(venueId, "GENERAL", i, startSeatNumber);
            sb.append(aValue);
            appendEndOfStatement(i, endSeatNumber, sb);
        }

        String result = sb.toString();
        out.println(result);
        print_end();
    }
}
