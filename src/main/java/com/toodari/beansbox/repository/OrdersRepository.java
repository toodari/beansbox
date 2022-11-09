package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Orders;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.search.SearchProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrdersRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Product>, SearchProductRepository {
}
