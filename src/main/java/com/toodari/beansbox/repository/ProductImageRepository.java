package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Modifying
    @Query("DELETE FROM ProductImage i WHERE i.product.pnum = :pnum ")
    void deleteByPnum(Long pnum);
}
