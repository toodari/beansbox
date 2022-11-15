package com.toodari.beansbox.controller;

import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.MemberMyPageDTO;
import com.toodari.beansbox.dto.MemberRegisterDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.service.MemberService;
import com.toodari.beansbox.validator.CheckMemberIdValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final CheckMemberIdValidator checkMemberIdValidator;

    @InitBinder("memberRegisterDTO")
    public void validatorBinder(WebDataBinder binder){
        binder.addValidators(checkMemberIdValidator);
    }

    @GetMapping("/login")
    public void loginGET(String error, String logout) {
        log.info("login get..");
        log.info("logout: " + logout);

    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/")
    public String index(){
        return "redirect:/member/list";
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list...");
         model.addAttribute("result", memberService.getList(pageRequestDTO));
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/register")
    public void registerGET(Model model){
        log.info("register get...");
        model.addAttribute("memberRegisterDTO", new MemberRegisterDTO());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/register")
    public String registerPOST(@Valid MemberRegisterDTO memberRegisterDTO, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        log.info("register post...");
        log.info(memberRegisterDTO);

        if(errors.hasErrors()) {
            log.info(errors);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key: validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "member/register";
        }
        memberService.register(memberRegisterDTO);
        redirectAttributes.addFlashAttribute("registered","registered");
        return "redirect:/member/list";
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/modify")
    public void read(String mid, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("mid: "+mid);
        MemberModifyDTO memberModifyDTO = memberService.read(mid);
        log.info("dto : " + memberModifyDTO.getMrole());
        log.info("dto : " + memberModifyDTO);
        model.addAttribute("memberModifyDTO", memberModifyDTO);
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/modify")
    public String modify(@Valid MemberModifyDTO memberModifyDTO, Errors errors, Model model, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        log.info("post modify...");
        log.info("dto: " + memberModifyDTO);

        redirectAttributes.addAttribute("mid", memberModifyDTO.getMid());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        model.addAttribute("memberModifyDTO", memberModifyDTO);

        if(errors.hasErrors()) {
            log.info("modify error....");
            log.info("Errors : " + errors);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key: validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }

            return "member/modify";
        }
        memberService.modify(memberModifyDTO);
        redirectAttributes.addFlashAttribute("modified","modified");
        return "redirect:/member/list";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/mypage")
    public void myRead(Authentication authentication, Model model) {
        String mid = authentication.getName();
        log.info("mypagemid: " + mid);
        MemberMyPageDTO memberMyPageDTO = memberService.myRead(mid);
        model.addAttribute("memberMyPageDTO", memberMyPageDTO);
        log.info("=====================================================");
        log.info(model.getAttribute("memberMyPageDTO"));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/mypage")
    public String myModify(@Valid MemberMyPageDTO memberMyPageDTO, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        log.info("post myModify...");
        log.info("dto: " + memberMyPageDTO);

        redirectAttributes.addAttribute("mid", memberMyPageDTO.getMid());
        if(errors.hasErrors()) {
            model.addAttribute("memberMyPageDTO", memberMyPageDTO);
            Map<String, String> validatorResult = memberService.validateHandling(errors);
            for(String key: validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "member/mypage";
        }
        memberService.myModify(memberMyPageDTO);
        redirectAttributes.addFlashAttribute("modified","modified");
        return "redirect:/product/list";
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/remove")
    public String remove(String mid, RedirectAttributes redirectAttributes){
        log.info("mid: " + mid);
        memberService.remove(mid);

        redirectAttributes.addFlashAttribute("deleted", "deleted");
        return "redirect:/member/list";
    }
}
