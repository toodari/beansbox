package com.toodari.beansbox.dto;

import com.toodari.beansbox.entity.MemberRole;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class MemberRegisterDTO {
    private Long mnum;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(min=4,max=16, message = "아이디는 4자 이상, 16자 이하로 입력해주세요")
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

    @NotNull(message = "입사한 연도는 필수 입력 값입니다.")
    @Positive(message = "올바른 형식의 날짜를 입력해 주세요")
    @Min(value = 2020, message = "2020년 이후의 날짜를 입력해 주세요")
    private Long myear;

    @NotNull(message = "입사한 달은 필수 입력 값입니다.")
    @Positive(message = "올바른 형식의 날짜를 입력해 주세요")
    @Max(value = 12, message = "올바른 형식의 날짜를 입력해 주세요")
    private Long mmonth;

    @NotNull(message = "입사한 날짜는 필수 입력 값입니다.")
    @Positive(message = "올바른 형식의 날짜를 입력해 주세요")
    @Max(value = 31, message = "올바른 형식의 날짜를 입력해 주세요")
    private Long mday;
}
