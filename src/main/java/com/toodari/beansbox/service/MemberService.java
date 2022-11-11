package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.Member;
import org.springframework.validation.Errors;

import java.util.Map;

public interface MemberService {
    static class MidExistException extends Exception{

    }

    PageResultDTO<MemberRegisterDTO, Member> getList(PageRequestDTO requestDTO);

    void register(MemberRegisterDTO memberRegisterDTO);

    void modify(MemberModifyDTO memberModifyDTO);

    void myModify(MemberMyPageDTO memberMyPageDTO);

    void remove(String mid);

    MemberModifyDTO read(String mid);

    MemberMyPageDTO myRead(String mid);

    public Map<String, String> validateHandling(Errors errors);

}
