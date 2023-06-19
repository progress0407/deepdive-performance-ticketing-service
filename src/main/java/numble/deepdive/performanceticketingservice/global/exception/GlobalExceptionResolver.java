package numble.deepdive.performanceticketingservice.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver {

    @ExceptionHandler(UnAuthorizationException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionResponse handleUserException(UnAuthorizationException exception) {

        log.info("NotMatchPasswordException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }
}
