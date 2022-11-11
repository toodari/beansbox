package com.toodari.beansbox.member.service;

import com.toodari.beansbox.dto.MemberRegisterDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTests {
    @Autowired
    private MemberService memberService;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<MemberRegisterDTO, Member> resultDTO = memberService.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());
        System.out.println("---------------------------------------");

        for (MemberRegisterDTO memberRegisterDTO : resultDTO.getDtoList()) {
            System.out.println(memberRegisterDTO);
        }
        System.out.println("=======================================");

        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }

    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .keyword("테스트")
                .build();

        PageResultDTO<MemberRegisterDTO, Member> resultDTO = memberService.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());
        System.out.println("---------------------------------------");

        for (MemberRegisterDTO memberRegisterDTO : resultDTO.getDtoList()) {
            System.out.println(memberRegisterDTO);
        }
        System.out.println("=======================================");

        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testMyRead() {

    }
}
