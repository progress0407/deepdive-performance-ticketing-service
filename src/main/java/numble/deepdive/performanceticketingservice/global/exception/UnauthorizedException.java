package numble.deepdive.performanceticketingservice.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnauthorizedException extends RuntimeException {

    private final static HttpStatus httpStatus = UNAUTHORIZED;

    public UnauthorizedException(String message) {
        super(selectMessage(message));
    }

    private static String selectMessage(String message) {
        if (message.isBlank()) {
            return "[401] 권한이 없습니다.";
        }
        return message;
    }
}
