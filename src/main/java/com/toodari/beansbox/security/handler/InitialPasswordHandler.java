package com.toodari.beansbox.security.handler;

import com.toodari.beansbox.security.dto.MemberSecurityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class InitialPasswordHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info(authentication.getPrincipal());

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        String encodedPw = memberSecurityDTO.getMpw();

        if(encodedPw.equals("mitPass123!") || passwordEncoder.matches("mitPass123!", encodedPw)){
            log.info("Should change password");
            response.sendRedirect("/member/mypage");
        } else {
            response.sendRedirect("/");
        }
    }
}
