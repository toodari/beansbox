package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product, Long> , QuerydslPredicateExecutor<Product>{

}
