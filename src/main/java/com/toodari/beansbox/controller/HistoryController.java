package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.service.AdjustService;
import com.toodari.beansbox.service.InputService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/history")
@Log4j2
@RequiredArgsConstructor
public class HistoryController {

    private final AdjustService service;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/read")
    public void read(){

        log.info("read..............");

    }
}
