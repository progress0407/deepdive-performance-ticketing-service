package numble.deepdive.performanceticketingservice.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.auth.application.AuthService;
import numble.deepdive.performanceticketingservice.auth.dto.LoginRequest;
import numble.deepdive.performanceticketingservice.auth.dto.LoginResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        String email = request.getEmail();
        String inputRawPassword = request.getPassword();

        String accessToken = authService.login(email, inputRawPassword);

        return new LoginResponse(accessToken);
    }
}
