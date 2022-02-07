package com.ssafy.challympic.config;

import com.ssafy.challympic.api.UserApiController;
import com.ssafy.challympic.jwt.JwtAuthenticationFilter;
import com.ssafy.challympic.jwt.JwtAuthorizationFilter;
import com.ssafy.challympic.repository.UserAuthRepository;
import com.ssafy.challympic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록된다. 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다는 뜻(Secured 어노테이션 활성화), preAuthorize라는 어노테이션 활설화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    private final UserAuthRepository userAuthRepository;

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    public BCryptPasswordEncoder encoderPwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
        http
                .csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 사용하지 않겠다.
                .and()
                .addFilter(corsFilter)
                .formLogin().disable() // form tag 만들어 로그인하지 않겠다.
                .httpBasic().disable() // 기본인증 방식 -> 우리는 barear 사용
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager 파라미터를 넘겨야한다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userAuthRepository))
                .authorizeRequests()
                .antMatchers("/challympic/user/*")
                .access("hasRole('USER')")
                .anyRequest().permitAll();
    }

}
