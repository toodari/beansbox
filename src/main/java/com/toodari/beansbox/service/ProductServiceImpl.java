package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;
import com.toodari.beansbox.repository.ProductImageRepository;
import com.toodari.beansbox.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;

    @Transactional
    @Override
    public Long register(ProductDTO productDTO) {
        log.info(productDTO.getPactive());

        Map<String, Object> entityMap = dtoToEntity(productDTO);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            imageRepository.save(productImage);
        });

        return product.getPnum();
    }

    @Override
    public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO){

        log.info(requestDTO);

        Function<Object[], ProductDTO> fn = (arr -> entitiesToDTO(
                (Product)arr[0] ,
                (List<ProductImage>)(Arrays.asList((ProductImage)arr[1])))
        );

        Page<Object[]> result = productRepository.searchPage(
                requestDTO.getType(),
                requestDTO.getKeyword(),
                requestDTO.getPageable(Sort.by("pnum").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long pnum) {
        List<Object[]> result = productRepository.getProductWithImage(pnum);

        Product product = (Product) result.get(0)[0];

        List<ProductImage> productImageList = new ArrayList<>();

        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[1];
            productImageList.add(productImage);
        });

        return entitiesToDTO(product, productImageList);
    }

    @Transactional
    @Override
    public Long modify(ProductDTO dto) {
        List<Object[]> result = productRepository.getProductWithImage(dto.getPnum());

        Product product = (Product) result.get(0)[0];
        List<ProductImage> productImageList = new ArrayList<>();
        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[1];
            productImageList.add(productImage);
        });

        product.changeProduct(dto);

        productRepository.save(product);
        productImageList.forEach(productImage -> {
            imageRepository.save(productImage);
        });

        return product.getPnum();
    }

    @Transactional
    @Override
    public Long copy(ProductDTO productDTO) {
        Map<String, Object> entityMap = dtoToEntity(productDTO);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            imageRepository.save(productImage);
        });

        return product.getPnum();
    }

    @Override
    public void remove(Long pnum) {
        productRepository.deleteById(pnum);
    }

    @Transactional
    @Override
    public void removeWithImages(Long pnum) {

        List<Object[]> result = productRepository.getProductWithImage(pnum);
        Product product = (Product) result.get(0)[0];
        product.changeActive(0);
        productRepository.save(product);

    }

}