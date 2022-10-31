package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Test
    public void testRegister(){
        ProductDTO productDTO = ProductDTO.builder()
                .p_name("Sample Name...")
                .p_cat("Sample Category...")
                .p_quantity(100L)
                .p_cost(1000L)
                .p_price(10000L)
                .p_active(1)
                .build();

        System.out.println(service.register(productDTO));
    }
}
