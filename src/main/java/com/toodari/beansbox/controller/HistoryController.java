package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.OrdersDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.service.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
@Log4j2
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/")
    public String index(){
        return "redirect:/input/list";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", historyService.getList(pageRequestDTO));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/read")
    public void read(long onum, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("onum: "+onum);

        OrdersDTO ordersDTO = historyService.read(onum);
        log.info("---------------------");
        log.info(ordersDTO);
        model.addAttribute("category", ordersDTO.getOcat());
        model.addAttribute("pic", ordersDTO.getMname());
        model.addAttribute("regdate", ordersDTO.getRegDate());
        model.addAttribute("resultSet", historyService.getDetailList(onum));
    }
}
