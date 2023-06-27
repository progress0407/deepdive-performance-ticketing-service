package numble.deepdive.performanceticketingservice.concurrency;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.lang.System.out;

/**
 * 동시성 데이터를 생성하기 위한 클래스입니다
 * <br>
 * 사용법은 main 함수 자체적으로 데이터를 생성합니다.
 * <br>
 * 생성된 파일은 src/test/resources/sql/ 하위에 위치합니다.
 * <br>
 * @see ConcurrencyTest
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConcurrencyDataUtils {

    public static void main(String[] args) {

//        print_venue_seat(250);
        print_performance_seat(250);
//        print_performance_seat_with_opt_lock(250);
    }

    public static void print_venue_seat(int upperBoundNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();

        sb.append("insert into venue_seat (venue_id, id, seat_number, seat_type, created_at, updated_at)\nvalues ");

        for (int i = 1; i <= upperBoundNumber; i++) {
            String oneTerm = String.format("(10001, %d, 'A%d', 'GENERAL', now(), now())", 10000 + i, i);
            sb.append(oneTerm);
            // 문장 마침표 처리
            appendEndOfStatement(upperBoundNumber, sb, i);
        }

        String result = sb.toString();
        out.println(result);
        print_end();
    }

    private static void print_performance_seat(int upperBoundNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();

        sb.append("insert into performance_seat(performance_id, id, seat_number, seat_type, booking_status, created_at, updated_at)\nvalues ");

        for (int i = 1; i <= upperBoundNumber; i++) {
            String oneTerm = String.format("(10001, %d, 'A%d', 'GENERAL', 'AVAILABLE', now(), now())", 10000 + i, i);
            sb.append(oneTerm);
            appendEndOfStatement(upperBoundNumber, sb, i);
        }

        out.println(sb);
        print_end();
    }

    private static void print_performance_seat_with_opt_lock(int upperBoundNumber) {

        print_start();

        StringBuilder sb = new StringBuilder();

        sb.append("insert into performance_seat(performance_id, id, seat_number, seat_type, booking_status, created_at, updated_at, version)\nvalues ");

        for (int i = 1; i <= upperBoundNumber; i++) {
            String oneTerm = String.format("(10001, %d, 'A%d', 'GENERAL', 'AVAILABLE', now(), now(), 0)", 10000 + i, i);
            sb.append(oneTerm);
            appendEndOfStatement(upperBoundNumber, sb, i);
        }

        out.println(sb);
        print_end();
    }

    private static void appendEndOfStatement(int upperBoundNumber, StringBuilder sb, int i) {
        if (i == upperBoundNumber) {
            sb.append(";\n");
        } else {
            sb.append(", \n");
        }
    }

    private static void print_start() {
        String string = "\n\n\n" +
                "=========================================================================\n" +
                "==================== GENERATE SQL STATEMENT :: START ====================\n" +
                "=========================================================================" +
                "\n\n\n";
        out.println(string);
    }

    private static void print_end() {
        String sting = "\n\n\n" +
                "=======================================================================\n" +
                "==================== GENERATE SQL STATEMENT :: END ====================\n" +
                "=======================================================================" +
                "\n\n\n";
        out.println(sting);
    }
}
