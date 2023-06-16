package numble.deepdive.performanceticketingservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import numble.deepdive.performanceticketingservice.user.exception.NotMatchPasswordException;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ExceptionResponse {

    private String message;

    public ExceptionResponse(NotMatchPasswordException exception) {
        this.message = exception.getMessage();
    }
}
