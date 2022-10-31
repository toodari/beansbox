package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ProductDTO productDTO) {
        log.info("DTO......................");
        log.info(productDTO);

        Product product = modelMapper.map(productDTO, Product.class);

        log.info(product);
        productRepository.save(product);

        return product.getP_num();
    }

}
