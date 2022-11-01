package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"product", "orders"})
@Table(name = "ORDER_DETAIL")
public class OrderDetail {

    @Id
    @Column(name = "od_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long odnum;

    @Column(name = "od_quantity", nullable = false)
    private Long odquantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_num")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_num")
    private Orders orders;
}
