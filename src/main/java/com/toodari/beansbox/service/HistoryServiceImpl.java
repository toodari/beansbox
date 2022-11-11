package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.*;
import com.toodari.beansbox.repository.OrderDetailRepository;
import com.toodari.beansbox.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    @Override
    public PageResultDTO<OrdersDTO, Object[]> getList(PageRequestDTO requestDTO) {

        requestDTO.setPage(1);
        requestDTO.setSize(1000);

        log.info(requestDTO);

        Function<Object[], OrdersDTO> fn = (arr -> entitiesToDTO(
                (Orders) arr[0],
                (Member) arr[1],
                (Long) arr[2])
        );

        Page<Object[]> result = ordersRepository.getHistoryWithMember(
                requestDTO.getPageable(Sort.by("onum").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public OrdersDTO read(Long onum) {
            List<Object[]> result = ordersRepository.getHistoryByOnum(onum);

            Orders orders = (Orders) result.get(0)[0];
            Member member = (Member) result.get(0)[1];
            Long odcount = (Long) result.get(0)[2];

            return entitiesToDTO(orders, member, odcount);
    }

    @Transactional
    @Override
    public List<HistoryDTO> getDetailList(Long onum) {
        List<HistoryDTO> resultSet = new ArrayList<>();
        Optional<Orders> result = ordersRepository.findById(onum);
        Orders orders = result.get();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrders(orders);
        for (OrderDetail orderDetail : orderDetailList) {
            HistoryDTO historyDTO = HistoryDTO.builder()
                    .pname(orderDetail.getProduct().getPname())
                    .pactive(orderDetail.getProduct().getPactive())
                    .pcost(orderDetail.getProduct().getPcost())
                    .pprice(orderDetail.getProduct().getPprice())
                    .odquantity(orderDetail.getOdquantity())
                    .build();

            resultSet.add(historyDTO);
        }

        return resultSet;
    }
}
