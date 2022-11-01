package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    @GetMapping("/")
    public String index(){
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        log.info("list.............." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){
        log.info("register get......");
    }

    @PostMapping("/register")
    public String registerPost(ProductDTO dto, RedirectAttributes redirectAttributes){

        log.info("dto..." + dto);

        Long pnum = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", pnum);

        return "redirect:/product/list";
    }
    @GetMapping({"/read", "/modify", "/copy"})
    public void read(long pnum, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("pnum : " + pnum);

        ProductDTO dto = service.read(pnum);

        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(ProductDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post modify............");
        log.info("dto : " + dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("pnum", dto.getPnum());
        redirectAttributes.addFlashAttribute("modifymsg", dto);

        return "redirect:/product/read";
    }

    @PostMapping("/copy")
    public String copy(ProductDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        log.info("post copy............");
        log.info("dto : " + dto);

        Long pnum = service.copy(dto); //read에서 복제 버튼을 누른 번호
        log.info(dto);

        PageResultDTO result = service.getList(requestDTO);
        ProductDTO firstProduct = (ProductDTO) result.getDtoList().get(0);
        //새로 만든 글의 번호를 가져와야됨

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("pnum", firstProduct.getPnum()); //null 자리에 새로운 번호가 들어감
        redirectAttributes.addFlashAttribute("copymsg", pnum);

        return "redirect:/product/read";
    }



    @PostMapping("/remove")
    public String remove(long pnum, RedirectAttributes redirectAttributes){

        log.info("pnum: " + pnum);

        service.remove(pnum);

        redirectAttributes.addFlashAttribute("deletemsg", pnum);

        return "redirect:/product/list";
    }
}
