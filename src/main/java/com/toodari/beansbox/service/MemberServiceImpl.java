package com.toodari.beansbox.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.toodari.beansbox.dto.*;

import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.entity.MemberRole;
import com.toodari.beansbox.entity.QMember;
import com.toodari.beansbox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final ModelMapper modelMapper;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResultDTO<MemberRegisterDTO, Member> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("mnum").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Member> result = memberRepository.findAll(booleanBuilder, pageable);

        Function<Member, MemberRegisterDTO> fn = (entity -> modelMapper.map(entity, MemberRegisterDTO.class));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public void register(MemberRegisterDTO memberRegisterDTO){
        String mrole = memberRegisterDTO.getMrole();
        Set<MemberRole> roleSet = new HashSet<>();
        if(mrole.equals("OWNER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
            roleSet.add(MemberRole.OWNER);
        } else if(mrole.equals("MANAGER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
        } else if(mrole.equals("EMPLOYEE")) {
            roleSet.add(MemberRole.EMPLOYEE);
        }
        memberRegisterDTO.setRoleSet(roleSet);

        Member member = modelMapper.map(memberRegisterDTO, Member.class);
        member.changePassword(passwordEncoder.encode(memberRegisterDTO.getMpw()));

        log.info("=====================");
        log.info(member);
        log.info(member.getRoleSet());

        memberRepository.save(member);
    }

    @Override
    public MemberModifyDTO read(String mid) {
        Optional<Member> result = memberRepository.getWithRoles(mid);

        MemberModifyDTO dto = modelMapper.map(result.get(), MemberModifyDTO.class);

        if(result.isPresent()){
            if(result.get().getRoleSet().size() == 1) {
                dto.setMrole("EMPLOYEE");
            }
            if(result.get().getRoleSet().size() == 2) {
                dto.setMrole("MANAGER");
            }
            if(result.get().getRoleSet().size() == 3) {
                dto.setMrole("OWNER");
            }
        } else dto = null;
        return dto;
    }

    @Override
    public MemberMyPageDTO myRead(String mid) {
        Optional<Member> result = memberRepository.getWithRoles(mid);

        MemberMyPageDTO dto = modelMapper.map(result.get(), MemberMyPageDTO.class);

        if(result.isPresent()){
            if(result.get().getRoleSet().size() == 1) {
                dto.setMrole("EMPLOYEE");
            }
            if(result.get().getRoleSet().size() == 2) {
                dto.setMrole("MANAGER");
            }
            if(result.get().getRoleSet().size() == 3) {
                dto.setMrole("OWNER");
            }
        } else dto = null;

        return dto;
    }

    @Override
    public void modify(MemberModifyDTO memberModifyDTO) {
        String mrole = memberModifyDTO.getMrole();
        Set<MemberRole> roleSet = new HashSet<>();
        if(mrole.equals("OWNER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
            roleSet.add(MemberRole.OWNER);
        } else if(mrole.equals("MANAGER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
        } else if(mrole.equals("EMPLOYEE")) {
            roleSet.add(MemberRole.EMPLOYEE);
        }
        memberModifyDTO.setRoleSet(roleSet);
        Optional<Member> result = memberRepository.getWithRoles(memberModifyDTO.getMid());
        if(result.isPresent()){
            Member entity = result.get();
            entity.changeMember(memberModifyDTO);
            memberRepository.save(entity);
        }
    }

    @Override
    public void myModify(MemberMyPageDTO memberMyPageDTO) {
        String mrole = memberMyPageDTO.getMrole();
        Set<MemberRole> roleSet = new HashSet<>();
        if(mrole.equals("OWNER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
            roleSet.add(MemberRole.OWNER);
        } else if(mrole.equals("MANAGER")) {
            roleSet.add(MemberRole.EMPLOYEE);
            roleSet.add(MemberRole.MANAGER);
        } else if(mrole.equals("EMPLOYEE")) {
            roleSet.add(MemberRole.EMPLOYEE);
        }
        memberMyPageDTO.setRoleSet(roleSet);
        Optional<Member> result = memberRepository.getWithRoles(memberMyPageDTO.getMid());
        if(result.isPresent()){
            Member entity = result.get();
            entity.changeMyPage(memberMyPageDTO);
            entity.changePassword(passwordEncoder.encode(memberMyPageDTO.getMpw()));
            memberRepository.save(entity);
        }
    }

    @Transactional
    @Override
    public void remove(String mid) {
        memberRepository.deleteByMid(mid);
    }

    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for(FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QMember qMember = QMember.member;

        BooleanExpression expression = qMember.mnum.gt(0L);

        booleanBuilder.and(expression);

        if(keyword == null || keyword.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(keyword.contains("직원")){
            conditionBuilder.or(qMember.roleSet.size().eq(1));
        }
        if(keyword.contains("매니저")){
            conditionBuilder.or(qMember.roleSet.size().eq(2));
        }
        if(keyword.contains("점장")){
            conditionBuilder.or(qMember.roleSet.size().eq(3));
        }
        conditionBuilder.or(qMember.mid.contains(keyword));
        conditionBuilder.or(qMember.mname.contains(keyword));

        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}
