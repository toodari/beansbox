package com.toodari.beansbox.controller;


import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

//    @PostMapping("/list")
//    public String listPost(RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("msg", "success");
//
//        return "redirect:/input/register";
//    }

//    @RequestMapping("/list")
//    public Map<String, Object> insert(@RequestParam("chkArr[]") List<String> chkArr) throws Exception {
//        Iterator<String> it =  chkArr.iterator();
//
//        //DB <- Genre processing
//        //controller processing
//    }


    @GetMapping("/register")
    public void register(@RequestParam List<String> pnum, Model model){

        log.info(pnum);
        log.info("register..............");

        model.addAttribute("checked", pnum);
    }

    @PostMapping("/register")
    public String registerPost(ProductDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

        Long pnum = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", pnum);

        return "redirect:/input/list";
    }

}
