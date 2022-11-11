package com.toodari.beansbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping({"/", "/index"})
    public String main() {
        log.info("main list...");

        return "redirect:/product/list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/403")
    public void accessDenied(){
        log.info("accessDenied...");
    }

}