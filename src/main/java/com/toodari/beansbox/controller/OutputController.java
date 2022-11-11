package com.toodari.beansbox.controller;


import com.toodari.beansbox.dto.OrdersDTO;
import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.service.MemberService;
import com.toodari.beansbox.service.OutputService;
import com.toodari.beansbox.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/output")
@Log4j2
@RequiredArgsConstructor
public class OutputController {


    private final OutputService outputService;
    private final MemberService memberService;
    private final ProductService productService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/")
    public String index(){
        return "redirect:/output/list";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", outputService.getList(pageRequestDTO));

    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/register")
    public void register(Authentication authentication, @RequestParam @ModelAttribute("checked") List<String> pnumList, Model model){
        log.info("register..............");
        log.info(pnumList);

        String mid = authentication.getName();
        log.info("PIC mid: " + mid);
        MemberModifyDTO memberModifyDTO = memberService.read(mid);
        Long mnum = memberModifyDTO.getMnum();

        model.addAttribute("category", "output");

        model.addAttribute("mnumber", mnum);
        model.addAttribute("resultSet", outputService.getChkList(pnumList));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/register")
    public String registerPost(OrdersDTO ordersDTO, @RequestParam List<Long> pnum, @RequestParam List<Long> pquantity,
                               RedirectAttributes redirectAttributes, Model model){

        log.info("list post...");
        log.info(ordersDTO);

        Long onum = outputService.register(ordersDTO, pnum, pquantity);

        redirectAttributes.addFlashAttribute("registered", onum);

        return "redirect:/output/list";
    }

}
