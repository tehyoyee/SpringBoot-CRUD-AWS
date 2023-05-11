package springboot.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import springboot.domain.user.Role;

@RequiredArgsConstructor
@EnableWebSecurity      // 스프링 시큐리티 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.headers().frameOptions().disable()     // h2콘솔 사용하기위해 disable
				.and()
					.authorizeRequests()        // URL별 권한 관리를 설정하는 옵션 시작점. authorizedRequests가 선언되어야 antMatchers 옵션 사용가능
					.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
					.antMatchers("/api/v1/**").hasRole(Role.USER.name())    // anyMatchers : 권한 관리 대상을 지정하는 옵션 URL, HTTP 메소드별로 관리 가능
								// '/'등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었다
								// "api/v1/**"주소를 가진 API는 USER권한을 가진 사람만 가능하게 하였다.
					.anyRequest().authenticated()
						// anyRequest 설정된 값들 이외 나머지 URL들을 나타낸다. 여기서는 authentificated()를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용
						// 인증된 사용자 == 로그인한 사용자
				.and()
					.logout()
						.logoutSuccessUrl("/")      // 로그아웃 기능에 대한 여러 설정의 진입점. 성공시 / 주소로 이동
				.and()
					.oauth2Login()      // OAuth2 로그인 기능에 대한 여러 설정의 진입점
						.userInfoEndpoint()     // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 대의 설정들을 담당
							.userService(customOAuth2UserService);      // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
																		// 리소스 서버(=소셜서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다
	}
}
