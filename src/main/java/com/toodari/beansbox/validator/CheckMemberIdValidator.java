package com.toodari.beansbox.validator;

import com.toodari.beansbox.dto.MemberRegisterDTO;
import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component
public class CheckMemberIdValidator extends AbstractValidator<MemberRegisterDTO>{

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    protected void doValidate(MemberRegisterDTO dto, Errors errors) {

        Member member = modelMapper.map(dto, Member.class);

        if(memberRepository.existsByMid(member.getMid())) {
            errors.rejectValue("mid","아이디 중복 오류","이미 사용중인 아이디입니다.");
        }
    }
}
