package com.toodari.beansbox.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long m_num;

    @Column(nullable = false)
    private String m_id;

    @Column(nullable = false)
    private String m_pw;

    @Column(nullable = false)
    private String m_name;

    @Column(nullable = false)
    private String m_phone;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long m_year;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long m_month;

    @Column(nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long m_day;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="member_role", joinColumns = @JoinColumn(name= "m_num", referencedColumnName = "m_num"))
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addMemberRole(MemberRole memberRole){
        roleSet.add(memberRole);
    }
}
