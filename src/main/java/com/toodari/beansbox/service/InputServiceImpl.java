package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.*;
import com.toodari.beansbox.repository.MemberRepository;
import com.toodari.beansbox.repository.OrderDetailRepository;
import com.toodari.beansbox.repository.OrdersRepository;
import com.toodari.beansbox.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Log4j2
@Service
@RequiredArgsConstructor
public class InputServiceImpl implements InputService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrdersRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Long register(OrdersDTO ordersDTO, List<Long> pnum, List<Long> pquantity) {
        Orders orders = modelMapper.map(ordersDTO, Orders.class);
        ordersRepository.save(orders);
        Long onum = orders.getOnum();

        for (int i = 0; i < pnum.size(); i++) {
            InputOrderDetailDTO inputOrderDetailDTO = InputOrderDetailDTO.builder()
                    .odquantity(pquantity.get(i))
                    .pnum(pnum.get(i))
                    .onum(onum).build();

            OrderDetail orderDetail = modelMapper.map(inputOrderDetailDTO, OrderDetail.class);
            orderDetailRepository.save(orderDetail);

            List<Object[]> result = productRepository.getProductWithImage(pnum.get(i));
            Product product = (Product) result.get(0)[0];
            Long finalQuantity = product.getPquantity() + pquantity.get(i);
            log.info(finalQuantity);
            product.changeQuantity(finalQuantity);
            productRepository.save(product);
        }

        return onum;
    }

    @Override
    public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO) {

        requestDTO.setPage(1);
        requestDTO.setSize(1000);

        log.info(requestDTO);

        Function<Object[], ProductDTO> fn = (arr -> entitiesToDTO(
                (Product) arr[0],
                (List<ProductImage>) (Arrays.asList((ProductImage) arr[1])))
        );

        Page<Object[]> result = productRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("pnum").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public List<ProductDTO> getChkList(List<String> pnumList) {

        List<ProductDTO> resultSet = new ArrayList<>();

        for (String pnum : pnumList) {
            List<Object[]> result = productRepository.getProductWithImage(Long.parseLong(pnum));
            Product product = (Product) result.get(0)[0];
            List<ProductImage> productImageList = new ArrayList<>();
            result.forEach(arr -> {
                ProductImage productImage = (ProductImage) arr[1];
                productImageList.add(productImage);
            });
            ProductDTO dto = entitiesToDTO(product, productImageList);
            resultSet.add(dto);
        }

        return resultSet;
    }

}
