package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Orders;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.search.SearchProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long>, QuerydslPredicateExecutor<Product>, SearchProductRepository {

    @Query(value = "SELECT o, m, count(d) FROM Orders o LEFT JOIN o.member m LEFT JOIN OrderDetail d ON d.orders = o GROUP BY o",
            countQuery = "SELECT count (o) FROM Orders o")
    Page<Object[]> getHistoryWithMember(Pageable pageable);


    @Query("SELECT o, m, count(d) FROM Orders o LEFT JOIN o.member m LEFT JOIN OrderDetail d ON d.orders = o " +
            "where o.onum = :onum group by d")
    List<Object[]> getHistoryByOnum(Long onum);


}
