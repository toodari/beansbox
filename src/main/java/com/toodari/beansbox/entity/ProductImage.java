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
    @Column(name = "img_num")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgnum;

    @Column(name = "img_name", nullable = false)
    private String imgname;

    @Column(name = "img_path", nullable = false)
    private String imgpath;

    @Column(name = "img_uuid", nullable = false)
    private String imguuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_num")
    private Product product;
}
