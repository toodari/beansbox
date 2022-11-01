package com.toodari.beansbox.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------------configure------------");

        http.formLogin();

        http.csrf().disable();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("web configure");
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //403(forbidden)에러는 서버에서 사용자의 요청을 거부했다는 의미로 현재 사용자가 로그인은 되어있지만 해당 작업을 수행할 수 없는 경우.
    //-> AccessDeniedHandler 인터페이스를 구현
    //-> Custom403Handler 클래스를 통해 에러페이지로 이동하게 처리

    // 워크북 707p부터 봐야함
}