package com.toodari.beansbox.member.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.MemberMyPageDTO;
import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.entity.MemberRole;

import com.toodari.beansbox.entity.QMember;
import com.toodari.beansbox.repository.MemberRepository;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static com.toodari.beansbox.entity.MemberRole.EMPLOYEE;
import static com.toodari.beansbox.entity.MemberRole.MANAGER;
import static com.toodari.beansbox.entity.MemberRole.OWNER;

@SpringBootTest

public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void insertMembers() {
        IntStream.rangeClosed(10, 99).forEach(i -> {
            Member member = Member.builder()
                    .mid("member" + i)
                    .mpw(passwordEncoder.encode("mitPass123!"))
                    .mname("직원" + i)
                    .mphone("010123456" + i)
                    .myear(1900 + (long) i)
                    .mmonth((long) i % 12 + 1)
                    .mday((long) i % 30 + 1)
                    .build();

            member.addMemberRole(EMPLOYEE);

            if (i <= 19) {
                member.addMemberRole(MANAGER);
            }
            if (i <= 13) {
                member.addMemberRole(OWNER);
            }
            memberRepository.save(member);
        });

    }

    @Test
    public void insertAdmin(){
        Member member = Member.builder()
                .mid("admin")
                .mpw(passwordEncoder.encode("mitPass123!"))
                .mname("관리자")
                .mphone("01012345678")
                .myear(2022L)
                .mmonth(11L)
                .mday(11L)
                .build();

        member.addMemberRole(EMPLOYEE);
        member.addMemberRole(MANAGER);
        member.addMemberRole(OWNER);

        memberRepository.save(member);
    }

    @Test
    public void testRead() {
        Optional<Member> result = memberRepository.getWithRoles("member9");

        Member member = result.orElseThrow();

//        log.info(member);
//        log.info(member.getRoleSet());
//
//        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }

    @Test
    public void testGetWithRoles() {
        String mid = "member99";
        Optional<Member> member = memberRepository.getWithRoles(mid);
        System.out.println("-----------------------------------");
        System.out.println(member);


        MemberMyPageDTO dto = modelMapper.map(member.get(), MemberMyPageDTO.class);
        System.out.println("-----------------------------------");
        System.out.println(dto);

        if (member.isPresent()) {
            if (member.get().getRoleSet().size() == 1) {
                dto.setMrole("EMPLOYEE");
            }
            if (member.get().getRoleSet().size() == 2) {
                dto.setMrole("MANAGER");
            }
            if (member.get().getRoleSet().size() == 3) {
                dto.setMrole("OWNER");
            }
        } else dto = null;

        System.out.println("-----------------------------------");
        System.out.println(dto);
    }

    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mnum").descending());

        QMember qmember = QMember.member;

        String keyword = "직원";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exId = qmember.mid.contains(keyword);
        BooleanExpression exName = qmember.mname.contains(keyword);
        BooleanExpression exAll = exId.or(exName);
        builder.and(exAll);
        builder.and(qmember.mnum.gt(0L));

        Page<Member> result = memberRepository.findAll(builder, pageable);
        result.stream().forEach(member -> {
            System.out.println(member);
        });
    }

    @Test
    public void testModify() {
        MemberModifyDTO memberModifyDTO = new MemberModifyDTO();
        memberModifyDTO.setMname("수정테스트");
        memberModifyDTO.setMphone("01011112222");
        memberModifyDTO.setMyear(2022L);
        memberModifyDTO.setMmonth(10L);
        memberModifyDTO.setMday(30L);
        Set<MemberRole> roleSet = new HashSet<>();
        roleSet.add(EMPLOYEE);
        roleSet.add(MANAGER);
        memberModifyDTO.setRoleSet(roleSet);

        Optional<Member> result = memberRepository.getWithRoles("member8");
        if (result.isPresent()) {
            Member member = result.get();
            member.changeMember(memberModifyDTO);
            memberRepository.save(member);
        }


    }
}
