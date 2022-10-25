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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long od_num;

    @Column(nullable = false)
    private Long od_quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_num")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "o_num")
    private Orders orders;
}
