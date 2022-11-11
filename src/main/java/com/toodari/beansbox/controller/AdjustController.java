package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.AdjustOrdersDTO;
import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.service.AdjustService;
import com.toodari.beansbox.service.MemberService;
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
@RequestMapping("/adjust")
@Log4j2
@RequiredArgsConstructor
public class AdjustController {
    private final AdjustService adjustService;
    private final MemberService memberService;
    private final ProductService productService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/")
    public String index(){
        return "redirect:/adjust/list";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", adjustService.getList(pageRequestDTO));

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

        // O_CAT에 넣을 카테고리 값
        model.addAttribute("category", "adjust");

        model.addAttribute("mnumber", mnum);
        model.addAttribute("resultSet", adjustService.getChkList(pnumList));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/register")
    public String registerPost(AdjustOrdersDTO adjustOrdersDTO, @RequestParam List<Long> pnum, @RequestParam List<Long> pquantity,
                               RedirectAttributes redirectAttributes){

        log.info("list post...");
        log.info(adjustOrdersDTO);

        Long onum = adjustService.register(adjustOrdersDTO, pnum, pquantity);

        redirectAttributes.addFlashAttribute("registered", onum);

        return "redirect:/adjust/list";
    }
}
