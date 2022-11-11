package com.toodari.beansbox.entity;

import com.toodari.beansbox.dto.ProductDTO;
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

    @Column(name = "p_active", columnDefinition = "TINYINT default 1")
    private int pactive;

    public void changeProduct(ProductDTO productDTO) {
        this.pname = productDTO.getPname();
        this.pcat = productDTO.getPcat();
        this.pcost = productDTO.getPcost();
        this.pprice = productDTO.getPprice();
    }

    public void changeQuantity(Long quantity) {
        this.pquantity = quantity;
    }
    public void changeActive(int active) {
        this.pactive = active;
    }

    @PrePersist
    public void prePersist() {
        this.pactive = (this.pactive == 0) ? 1 : this.pactive;
    }
}
