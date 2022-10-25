package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long p_num;

    @Column(nullable = false)
    private String p_name;

    @Column(nullable = false)
    private String p_cat;

    @Column(nullable = false)
    private Long p_quantity;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long p_cost;

    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long p_price;

    @Column(nullable = false, columnDefinition = "TINYINT", length = 1)
    private int p_active;

}
