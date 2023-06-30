package numble.deepdive.performanceticketingservice.support.datainit;

import static java.lang.System.out;

public abstract class AbstractDataInitUtils {

    protected static final String INSERT_INTO_VENUE_SEAT_PREVIOUS_CLAUSE = "insert into venue_seat (venue_id, id, seat_number, seat_type, created_at, updated_at)\nvalues ";

    protected static void appendEndOfStatement(int currentIndex, int endIndexNumber, StringBuilder sb) {
        if (currentIndex == endIndexNumber) {
            sb.append(";\n");
        } else {
            sb.append(", \n");
        }
    }

    protected static void print_start() {
        String string = "\n\n\n" +
                "=========================================================================\n" +
                "==================== GENERATE SQL STATEMENT :: START ====================\n" +
                "=========================================================================" +
                "\n\n\n";
        // 파란 색으로 print
        printInBlue(string);
    }

    protected static void print_end() {
        String sting = "\n\n\n" +
                "=======================================================================\n" +
                "==================== GENERATE SQL STATEMENT :: END ====================\n" +
                "=======================================================================" +
                "\n\n\n";
        printInBlue(sting);
    }

    protected static String formatAValue(int venueId, String venueType, int currentIndex, int startSeatNumber) {

        return String.format("(%d, %d, 'A%d', '%s', now(), now())",
                venueId, (startSeatNumber - 1) + currentIndex, currentIndex, venueType);
    }

    private static void printInBlue(String string) {
        out.println("\u001B[34m" + string + "\u001B[0m");
    }
}
