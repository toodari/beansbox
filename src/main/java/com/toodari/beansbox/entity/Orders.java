package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
public class Orders extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long o_num;

    @Column(nullable = false)
    private String o_cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_num")
    private Member member;

}
