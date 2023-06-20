package numble.deepdive.performanceticketingservice.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class BadRequestException extends RuntimeException {

    private final static HttpStatus httpStatus = BAD_REQUEST;

    public BadRequestException() {
        super("[400] 잘못된 요청입니다.");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
