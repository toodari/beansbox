package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.entity.Orders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class OrdersRepositoryTests {
    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    @Transactional
    public void testWithDetailCount() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("onum").descending());

        Page<Object[]> result = ordersRepository.getHistoryWithMember(pageable);

        result.get().forEach(row -> {
            Object[] arr = (Object[]) row;
            System.out.println("-----------------------");
            System.out.println(Arrays.toString(arr));
        });
    }

    @Transactional
    @Test
    public void readTest() {
        Long onum = 4L;

        List<Object[]> result = ordersRepository.getHistoryByOnum(onum);

        Orders orders = (Orders) result.get(0)[0];
        Member member = (Member) result.get(0)[1];
        Long odcount = (Long) result.get(0)[2];

        System.out.println("--------------------------");
        System.out.println(orders);
        System.out.println("--------------------------");
        System.out.println(member);
        System.out.println("--------------------------");
        System.out.println(odcount);
    }
}
