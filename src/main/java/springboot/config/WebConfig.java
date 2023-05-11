package springboot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springboot.config.auth.LoginUserArgumentResolver;

import java.util.List;

// HandlerMethodArgumentResolver는 항상 WebMvcConfigurer의 AddArgumentResolvers()를 통해 추가해야한다.
// 다른 HanlderMethodArgumentResolver가 필요하다면 같은 방식으로 추가해주면 된다

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final LoginUserArgumentResolver loginUserArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(loginUserArgumentResolver);
	}
}
