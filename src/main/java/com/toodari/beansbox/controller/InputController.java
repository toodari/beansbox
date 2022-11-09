package com.toodari.beansbox.controller;


import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.service.InputService;
import com.toodari.beansbox.service.MemberService;
import com.toodari.beansbox.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/input")
@Log4j2
@RequiredArgsConstructor
public class InputController {


    private final InputService inputService;
    private final MemberService memberService;
    private final ProductService productService;

    @GetMapping("/")
    public String index(){
        return "redirect:/input/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", inputService.getList(pageRequestDTO));

    }

    @PostMapping("/list")
    public String listPost(Authentication authentication, @RequestParam ("ocat") String ocat, @RequestParam ("pnum") List<Long> pnum, @RequestParam ("pinput") List<Long> pinput,
                           RedirectAttributes redirectAttributes, Model model){

        log.info(ocat);
        log.info(pinput);

        String mid = authentication.getName();
        log.info("PIC mid: " + mid);
        MemberModifyDTO memberModifyDTO = memberService.read(mid);
        Long mnum = memberModifyDTO.getMnum();

        Long onum = inputService.register(mnum, ocat, pnum, pinput);

        redirectAttributes.addFlashAttribute("registered", "registered");

        return "redirect:/input/list";
    }

    @GetMapping("/register")
    public void register(@RequestParam @ModelAttribute("checked") List<String> pnumList, Model model){
        log.info("register..............");
        log.info(pnumList);

        // O_CAT에 넣을 카테고리 값
        model.addAttribute("category", "input");

        model.addAttribute("resultSet", inputService.getChkList(pnumList));
    }

}
