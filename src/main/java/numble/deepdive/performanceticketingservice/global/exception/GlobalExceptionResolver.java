package numble.deepdive.performanceticketingservice.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver { // TODO 이름 변경

    @ResponseStatus(BAD_REQUEST) // 400
    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse handleBadRequest(RuntimeException exception) {

        log.info("BadRequestException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }

    @ResponseStatus(UNAUTHORIZED) // 401
    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionResponse handleUnauthorized(RuntimeException exception) {

        log.info("UnauthorizedException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }

    @ResponseStatus(FORBIDDEN) // 403
    @ExceptionHandler(ForbiddenException.class)
    public ExceptionResponse handleForbidden(RuntimeException exception) {

        log.info("NotMatchPasswordException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }
}
