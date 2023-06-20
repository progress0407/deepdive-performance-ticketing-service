package numble.deepdive.performanceticketingservice.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver { // TODO 이름 변경

    @ExceptionHandler (UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED) // 401
    public ExceptionResponse handleUnauthorized(RuntimeException exception) {

        log.info("UnauthorizedException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }

    @ExceptionHandler (ForbiddenException.class)
    @ResponseStatus(FORBIDDEN) // 403
    public ExceptionResponse handleForbidden(RuntimeException exception) {

        log.info("NotMatchPasswordException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }
}
