package numble.deepdive.performanceticketingservice.user.exception;

import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.global.exception.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice(basePackages = "numble.deepdive.performanceticketingservice.user")
@Slf4j
public class UserExceptionResolver {

    @ExceptionHandler(NotMatchPasswordException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleUserException(NotMatchPasswordException exception) {

        log.info("NotMatchPasswordException: {}", exception.getMessage());

        return new ExceptionResponse(exception);
    }
}
