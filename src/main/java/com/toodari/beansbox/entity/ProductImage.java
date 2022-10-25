package com.toodari.beansbox.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "product")
@Table(name = "PRODUCT_IMAGE")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long img_num;

    @Column(nullable = false)
    private String img_name;

    @Column(nullable = false)
    private String img_path;

    @Column(nullable = false)
    private String img_uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_num")
    private Product product;
}
