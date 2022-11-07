package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.search.SearchProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , QuerydslPredicateExecutor<Product>, SearchProductRepository {

    @Query("select p, i from Product p " +
            "left outer join ProductImage i on i.product = p " +
            "group by p ")
    Page<Object[]> getListPage(Pageable pageable);
}
