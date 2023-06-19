package numble.deepdive.performanceticketingservice.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class ExceptionResponse {

    private String message;

    public ExceptionResponse(Exception exception) {
        this.message = exception.getMessage();
    }
}
