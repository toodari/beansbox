package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.OrderDetail;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.search.SearchProductRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, QuerydslPredicateExecutor<Product>, SearchProductRepository {

    @EntityGraph(attributePaths = {"orders"}, type = EntityGraph.EntityGraphType.FETCH)
    List<OrderDetail> findByProduct(Product product);
}
