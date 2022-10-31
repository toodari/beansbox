package com.toodari.beansbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
@Log4j2
public class ProductController {

    @GetMapping({"/","/list"})
    public String list(){
        log.info("........................................list");
        return "/product/list";
    }

    @GetMapping("/register")
    public String register(){
        log.info("..............................register");
        return "/product/register";
    }

    @GetMapping("/read")
    public String read() {
        log.info("..............................read");
        return "/product/read";
    }

    @GetMapping("/modify")
    public String modify() {
        log.info("..............................modify");
        return "/product/modify";
    }

    @GetMapping("/clone")
    public String clone() {
        log.info("..............................clone");
        return "/product/clone";
    }
}
