package numble.deepdive.performanceticketingservice.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.global.exception.ForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    /**
     * TODO
     * 인증 후, 토큰 인증한 유저의 정보를 ArgumentResolver 등을 이용해서 넣어보도록 하자
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String encodedToken = extractToken(request);

        log.info("access url: {} {}", request.getMethod(), request.getRequestURI());

        boolean validToken = jwtManager.isValidToken(encodedToken);

        if (validToken) {
            return true;
        }

        throw new ForbiddenException();
    }

    private static String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }
}
