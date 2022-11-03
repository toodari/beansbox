package com.toodari.beansbox.repository;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Product product = Product.builder()
                    .pname("My Name..." + i)
                    .pcat("Category..." + i)
                    .pquantity(100L)
                    .pcost(1000L)
                    .pprice(10000L)
                    .pactive(1)
                    .build();

            System.out.println(productRepository.save(product));
        });
    }

    @Test
    public void getListPage(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        Pageable pageable = PageRequest.of(1,10);
        productRepository.getListPage(pageable);
    }

}
