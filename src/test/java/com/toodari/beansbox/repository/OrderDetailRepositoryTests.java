package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.OrderDetail;
import com.toodari.beansbox.entity.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderDetailRepositoryTests {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void testList() {
        Long onum = 4L;

        Optional<Orders> result = ordersRepository.findById(onum);
        System.out.println("-----------------------------------------");
        System.out.println(result);
        System.out.println("-----------------------------------------");

        Orders orders = result.get();
        System.out.println(orders);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrders(orders);
        System.out.println("-----------------------------------------");

        System.out.println(orderDetailList);
        System.out.println("-----------------------------------------");
        System.out.println(orderDetailList.get(0).getProduct());
    }
}
