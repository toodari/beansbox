package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;

public interface ProductService {
    Long register(ProductDTO dto);

    ProductDTO read(Long pnum);

    void modify(ProductDTO dto);

    Long copy(ProductDTO dto);

    void remove(Long pnum);

    PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO);



}
