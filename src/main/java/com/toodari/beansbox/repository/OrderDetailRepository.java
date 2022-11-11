package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.OrderDetail;
import com.toodari.beansbox.entity.Orders;
import com.toodari.beansbox.repository.search.SearchProductRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, QuerydslPredicateExecutor<Orders>, SearchProductRepository {

    //조회 처리
    @EntityGraph(attributePaths = {"product"}, type = EntityGraph.EntityGraphType.FETCH)
    List<OrderDetail> findByOrders(Orders orders);

    @Modifying
    @Query("DELETE FROM OrderDetail d WHERE d.product.pnum = :pnum ")
    void deleteByPnum(Long pnum);

}
