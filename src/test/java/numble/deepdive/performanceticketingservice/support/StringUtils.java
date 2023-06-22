package numble.deepdive.performanceticketingservice.support;

public class StringUtils {

    private StringUtils() {
    }

    public static String camelToSnake(String str) {

        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return str.replaceAll(regex, replacement).toLowerCase();
    }
}
