package numble.deepdive.performanceticketingservice.auth.exception;

import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.global.exception.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice(basePackages = "numble.deepdive.performanceticketingservice.user")
@Slf4j
public class UserExceptionResolver {

    @ExceptionHandler(NotMatchPasswordException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionResponse handleUserException(NotMatchPasswordException exception) {

        log.info("NotMatchPasswordException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }
}
