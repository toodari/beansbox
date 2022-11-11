package com.toodari.beansbox.entity;

import com.toodari.beansbox.dto.MemberModifyDTO;
import com.toodari.beansbox.dto.MemberMyPageDTO;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name = "m_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mnum;

    @Column(name = "m_id", nullable = false)
    private String mid;

    @Column(name = "m_pw", nullable = false)
    private String mpw;

    @Column(name = "m_name", nullable = false)
    private String mname;

    @Column(name = "m_phone", nullable = false)
    private String mphone;

    @Column(name = "m_year", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long myear;

    @Column(name = "m_month", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long mmonth;

    @Column(name = "m_day", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long mday;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="MEMBER_ROLE", joinColumns = @JoinColumn(name= "m_num", referencedColumnName = "m_num"))
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }

    public void changePassword(String mpw){
        this.mpw = mpw;
    }
    public void changeMember(MemberModifyDTO memberModifyDTO) {
        this.mname = memberModifyDTO.getMname();
        this.mphone = memberModifyDTO.getMphone();
        this.myear = memberModifyDTO.getMyear();
        this.mmonth = memberModifyDTO.getMmonth();
        this.mday = memberModifyDTO.getMday();
        this.roleSet = memberModifyDTO.getRoleSet();
    }

    public void changeMyPage(MemberMyPageDTO memberMyPageDTO) {
        this.mname = memberMyPageDTO.getMname();
        this.mphone = memberMyPageDTO.getMphone();
        this.myear = memberMyPageDTO.getMyear();
        this.mmonth = memberMyPageDTO.getMmonth();
        this.mday = memberMyPageDTO.getMday();
        this.roleSet = memberMyPageDTO.getRoleSet();
    }

}
