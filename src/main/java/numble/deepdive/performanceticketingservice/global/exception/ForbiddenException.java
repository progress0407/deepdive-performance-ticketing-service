package numble.deepdive.performanceticketingservice.global.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

public class ForbiddenException extends RuntimeException {

    private final HttpStatus httpStatus = FORBIDDEN;

    public ForbiddenException() {
        super("[403] 허가되지 않은 접근입니다.");
    }
}
