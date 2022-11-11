package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/")
    public String index(){
        return "redirect:/product/list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/register")
    public void register(){

        log.info("register get......");
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/register")
    public String registerPost(ProductDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

            Long pnum = service.register(dto);

        redirectAttributes.addFlashAttribute("registered", pnum);

        return "redirect:/product/list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping({"/read", "/modify", "/copy"})
    public void read(long pnum, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("pnum : " + pnum);

        ProductDTO dto = service.read(pnum);

        model.addAttribute("dto", dto);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/modify")
    public String modify(ProductDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify............");
        log.info("dto : " + dto);

        Long pnum = service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("pnum", dto.getPnum());
        redirectAttributes.addFlashAttribute("modifymsg", pnum);

        return "redirect:/product/read";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/copy")
    public String copy(ProductDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post copy............");
        log.info("dto : " + dto);

        Long pnum = service.copy(dto);
        log.info(dto);

        PageResultDTO result = service.getList(requestDTO);
        ProductDTO firstProduct = (ProductDTO) result.getDtoList().get(0);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("pnum", firstProduct.getPnum());
        redirectAttributes.addFlashAttribute("copymsg", firstProduct);

        return "redirect:/product/read";
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/remove")
    public String remove(long pnum, RedirectAttributes redirectAttributes){

        log.info("pnum: " + pnum);

        service.removeWithImages(pnum);

        redirectAttributes.addFlashAttribute("deletemsg", pnum);

        return "redirect:/product/list";
    }

}
