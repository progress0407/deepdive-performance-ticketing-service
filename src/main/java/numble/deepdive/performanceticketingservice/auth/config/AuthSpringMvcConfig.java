package numble.deepdive.performanceticketingservice.auth.config;

import lombok.RequiredArgsConstructor;
import numble.deepdive.performanceticketingservice.user.infrastructure.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthSpringMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final JwtManager jwtManager;
    private final UserRepository userRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor)
                .order(1)
                .addPathPatterns("/venues/**")
                .excludePathPatterns("/users/**", "/login/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new LoginUserArgumentResolver(jwtManager, userRepository));
    }
}
