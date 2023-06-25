package numble.deepdive.performanceticketingservice.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import numble.deepdive.performanceticketingservice.user.dto.UserCache;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserCacheRepository;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 로그인한 사용자에 대해 User를 반환하는 ArgumentResolver
 */
@Slf4j
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtManager jwtManager;
    private final UserCacheRepository userCacheRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        Class<?> parameterType = parameter.getParameterType();
        boolean isUserCacheType = UserCache.class.isAssignableFrom(parameterType);

        return isUserCacheType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String accessToken = extractToken(httpServletRequest);
        String email = jwtManager.parse(accessToken);

        return userCacheRepository.findUser(email);
    }

    private static String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }
}
