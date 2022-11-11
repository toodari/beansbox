package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.OrdersDTO;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrdersServiceTests {

    @Autowired
    private HistoryService service;

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        System.out.println("-----------------------1");

        PageResultDTO<OrdersDTO, Object[]> result = service.getList(pageRequestDTO);
        System.out.println("---------------------------2");

        for (OrdersDTO ordersDTO : result.getDtoList()) {
            System.out.println(ordersDTO);
        }
    }

    @Test
    public void testRead() {
        Long onum = 4L;

        OrdersDTO ordersDTO = service.read(onum);
        System.out.println(ordersDTO);
    }
}
