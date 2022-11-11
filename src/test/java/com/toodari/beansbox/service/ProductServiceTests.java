package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.dto.ProductImageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Test
    public void testRegister() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            ProductImageDTO productImageDTO1 = ProductImageDTO.builder()
                    .imguuid(UUID.randomUUID().toString())
                    .imgpath("C:\\Users\\mit\\Desktop")
                    .imgname("hodoo.jpg")
                    .build();
//        ProductImageDTO productImageDTO2 = ProductImageDTO.builder()
//                .imguuid(UUID.randomUUID().toString())
//                .imgpath("C:\\Users\\mit\\Desktop")
//                .imgname("dew.jpg")
//                .build();

            List<ProductImageDTO> productImageDTOList = new ArrayList<>();

            productImageDTOList.add(productImageDTO1);
//        productImageDTOList.add(productImageDTO2);

            ProductDTO productDTO = ProductDTO.builder()
                    .pname("Sample Name..." + i)
                    .pcat("Sample Category..." + i)
                    .pquantity(100L)
                    .pcost(1000L)
                    .pprice(10000L)
                    .pactive(1)
                    .imageDTOList(productImageDTOList)
                    .build();

            System.out.println(service.register(productDTO));
        });

    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<ProductDTO, Object[]> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("----------------------------------------------------------");

        for (ProductDTO productDTO : resultDTO.getDtoList()) {
            System.out.println(productDTO);
        }

        System.out.println("==========================================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

    @Test
    public void testRead() {
        ProductDTO productDTO = service.read(108L);
        System.out.println(productDTO);

    }

    @Test
    public void testModify() {
        ProductDTO productDTO = ProductDTO.builder()
                .pnum(108L)
                .pname("영광아밥먹자")
                .pcat("영광아밥먹자")
                .pcost(1000L)
                .pprice(10000L)
                .build();

        System.out.println("---------service 실행 전 DTO------------");
        System.out.println(productDTO);
        System.out.println("---------------------------------------");
        service.modify(productDTO);
        ProductDTO dto = service.read(108L);
        System.out.println(dto);

    }

    @Test
    public void testDelete() {

        service.remove(109L);

    }

    @Test
    public void testCopy() {
        ProductDTO productDTO = ProductDTO.builder()
                .pname("Sample Name...")
                .pcat("Sample Category...")
                .pquantity(100L)
                .pcost(1000L)
                .pprice(10000L)
                .pactive(1)
                .build();

        System.out.println(service.copy(productDTO));
    }

    @Test
    public void testSearch() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("nc")
                .keyword("영광")
                .build();

        PageResultDTO<ProductDTO, Object[]> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("----------------------------------------------------------");

        for (ProductDTO productDTO : resultDTO.getDtoList()) {
            System.out.println(productDTO);
        }

        System.out.println("==========================================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
