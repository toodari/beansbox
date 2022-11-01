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
}
