package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "PRODUCT")
public class Product extends BaseEntity {

    @Id
    @Column(name = "p_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pnum;

    @Column(name = "p_name", nullable = false)
    private String pname;

    @Column(name = "p_cat", nullable = false)
    private String pcat;

    @Column(name = "p_quantity", nullable = false)
    private Long pquantity;

    @Column(name = "p_cost", columnDefinition = "BIGINT UNSIGNED")
    private Long pcost;

    @Column(name = "p_price", columnDefinition = "BIGINT UNSIGNED")
    private Long pprice;

    @Column(name = "p_active", nullable = false, columnDefinition = "TINYINT", length = 1)
    private int pactive;

    public void changePName(String pname){
        this.pname = pname;
    }
    public void changePCat(String pcat){
        this.pcat = pcat;
    }
    public void changePCost(Long pcost){
        this.pcost = pcost;
    }
    public void changePPrice(Long pprice){
        this.pprice = pprice;
    }

}
