package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.Member;
import com.toodari.beansbox.entity.Orders;

import java.util.List;

public interface HistoryService {

    PageResultDTO<OrdersDTO, Object[]> getList(PageRequestDTO requestDTO);

    OrdersDTO read(Long onum);

    List<HistoryDTO> getDetailList(Long onum);

    default OrdersDTO entitiesToDTO(Orders orders, Member member, Long odcount){

        OrdersDTO ordersDTO = OrdersDTO.builder()
                .onum(orders.getOnum())
                .ocat(orders.getOcat())
                .odcount(odcount.intValue())
                .regDate(orders.getRegDate())
                .mname(member.getMname())
                .build();

        return ordersDTO;
    }

}
