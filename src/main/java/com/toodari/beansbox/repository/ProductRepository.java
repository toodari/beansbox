package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , QuerydslPredicateExecutor<Product>{

    @Query("select p, i from Product p left join ProductImage i on i.product = p where p.pnum = :pnum ")
    Page<Object[]> getListPage(Pageable pageable);
}
