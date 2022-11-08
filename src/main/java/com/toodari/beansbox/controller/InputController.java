package com.toodari.beansbox.controller;


import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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


    private final InputService service;

    @GetMapping("/")
    public String index(){
        return "redirect:/input/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register(@RequestParam @ModelAttribute("checked") List<String> pnumList, Model model){
        log.info("register..............");
        log.info(pnumList);

        model.addAttribute("resultSet", service.getChkList(pnumList));
    }

    @PostMapping("/register")
    public String registerPost(ProductDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

        Long pnum = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", pnum);

        return "redirect:/input/list";
    }

}
