package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "member")
@Table(name = "ORDERS")
public class Orders extends BaseEntity {
    @Id
    @Column(name = "o_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long onum;

    @Column(name = "o_cat", nullable = false)
    private String ocat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "m_num")
    private Member member;

}
