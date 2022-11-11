package com.toodari.beansbox.dto;

import com.toodari.beansbox.entity.MemberRole;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class MemberModifyDTO {
    private Long mnum;

    private String mid;

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
