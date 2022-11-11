package com.toodari.beansbox.config;

import com.toodari.beansbox.security.handler.Custom403Handler;
import com.toodari.beansbox.security.handler.InitialPasswordHandler;
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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("--------------configure------------");

        http.formLogin().loginPage("/member/login").successHandler(authenticationSuccessHandler());

        http.csrf().disable();

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

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

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new InitialPasswordHandler(passwordEncoder());
    }
}