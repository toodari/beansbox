package com.toodari.beansbox.repository;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    private ProductImageRepository imageRepository;

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

            ProductImage productImage = ProductImage.builder()
                    .imgname("hodoo.jpg")
                    .imguuid(UUID.randomUUID().toString())
                    .imgpath("C:\\Users\\mit\\Desktop")
                    .build();

            System.out.println("------------------------");
            System.out.println(product);
            System.out.println("------------------------");
            System.out.println(productImage);

            productRepository.save(product);
            imageRepository.save(productImage);

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

    @Test
    public void  testSearch1(){

        productRepository.search1();
    }

    @Test
    public void testSearchPage(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("pnum").descending()
                .and(Sort.by("pname").ascending()));

        Page<Object[]> result = productRepository.searchPage("nc", "Sample", pageable);
    }

}
