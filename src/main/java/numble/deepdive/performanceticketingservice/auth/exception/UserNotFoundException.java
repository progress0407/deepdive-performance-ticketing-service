package numble.deepdive.performanceticketingservice.auth.exception;

import numble.deepdive.performanceticketingservice.global.exception.UnauthorizedException;

public class UserNotFoundException extends UnauthorizedException {

    public UserNotFoundException() {
        super("사용자 계정이 존재하지 않습니다");
    }
}
