package numble.deepdive.performanceticketingservice.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class UnAuthorizationException extends RuntimeException {

    private final HttpStatus httpStatus = FORBIDDEN;

    public UnAuthorizationException() {
        super("허가되지 않은 접근입니다.");
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
