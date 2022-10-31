package com.toodari.beansbox.repository;

import com.toodari.beansbox.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Product product = Product.builder()
                    .p_name("My Name..." + i)
                    .p_cat("Category..." + i)
                    .p_quantity(100L)
                    .p_cost(1000L)
                    .p_price(10000L)
                    .p_active(1)
                    .build();

            System.out.println(productRepository.save(product));
        });
    }
}
