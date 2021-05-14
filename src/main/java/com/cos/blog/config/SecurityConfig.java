package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.config.oauth.PrincipalOauth2UserService;


@Configuration // 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 된다. (설정을 이 클래스에서 하겠다.)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크 하겠다는 뜻.
// 3개가 시큐리티 한 세트.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService PrincipalDetailService;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IoC가 됨. (비밀번호 해쉬)
	public BCryptPasswordEncoder encordPWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(PrincipalDetailService).passwordEncoder(encordPWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로챈다.
				.defaultSuccessUrl("/")
			.and()
				.oauth2Login()
				.loginPage("/loginForm") 
				.userInfoEndpoint()
				.userService(principalOauth2UserService);
	}
}
