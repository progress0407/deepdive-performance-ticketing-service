package numble.deepdive.performanceticketingservice.user.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    /**
     * TODO
     * 인증 후, 토큰 인증한 유저의 정보를 ArgumentResolver 등을 이용해서 넣어보도록 하자
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String encodedToken = request.getHeader("Authorization");

        return jwtManager.isValidToken(encodedToken);
    }
}
