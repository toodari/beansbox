package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;

public interface ProductService {
    Long register(ProductDTO dto);

}
