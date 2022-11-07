package com.toodari.beansbox.dto;

import com.toodari.beansbox.entity.MemberRole;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class MemberMyPageDTO {
    private Long mnum;

    private String mid;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String mpw;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]{2,20}$", message = "이름은 특수문자를 제외한 2~20자리여야 합니다.")
    private String mname;

    @NotBlank(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^010([0-9]{4})([0-9]{4})$", message = "올바른 휴대폰 번호를 입력해 주세요.")
    private String mphone;

    private String mrole;
    private Set<MemberRole> roleSet;

    private Long myear;

    private Long mmonth;

    private Long mday;
}
