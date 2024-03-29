package com.moayo.moayoeats.backend.global.config;


import com.moayo.moayoeats.backend.global.jwt.JwtUtil;
import com.moayo.moayoeats.backend.global.jwt.TokenService;
import com.moayo.moayoeats.backend.global.security.JwtAccessDeniedHandler;
import com.moayo.moayoeats.backend.global.security.JwtAuthenticationEntryPoint;
import com.moayo.moayoeats.backend.global.security.JwtAuthorizationFilter;
import com.moayo.moayoeats.backend.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, tokenService);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public JwtAccessDeniedHandler jwtAccessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement((sessionManagement) ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 인증 실패시 login 페이지로 이동, 로그인은 했지만 접근권한이 없을 시 메인 페이지로 이동
        http.exceptionHandling((exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(jwtAccessDeniedHandler()) // 인가 실패
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())) // 인증 실패
        );

        //login,signup 접근 허용 그 외 인증 필요
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll() // resources 접근 허용 설정
                    .requestMatchers("/").permitAll() // 메인 페이지 요청 허가
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/signup").permitAll()
                    .requestMatchers("/api/v1/users/sign-up/**").permitAll() // singup이후로 접근 허가
                    .requestMatchers("/api/v1/users/login/**").permitAll() // singup이후로 접근 허가
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .requestMatchers("/api/v1/test/**").permitAll()
                    .requestMatchers("/api/v1/readonly/**").permitAll()//글 조회시 인증정보 없이 읽을때
                    .requestMatchers("/moayo/**").permitAll()//프론트
//                .requestMatchers("/templates/**").permitAll()//test - html 접근 허가
                    .anyRequest().permitAll() // 그 외 모든 요청 인증처리
        );

        // 필터 관리
        http.addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}