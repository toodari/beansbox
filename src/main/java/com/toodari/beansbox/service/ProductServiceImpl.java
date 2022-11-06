package com.toodari.beansbox.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;
import com.toodari.beansbox.entity.QProduct;
import com.toodari.beansbox.repository.ProductImageRepository;
import com.toodari.beansbox.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Long register(ProductDTO productDTO) {

        Map<String, Object> entityMap = dtoToEntity(productDTO);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

        productRepository.save(product);

        productImageList.forEach(productImage -> {
            imageRepository.save(productImage);
        });

        return product.getPnum();
    }

//    @Override
//    public PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO){
//
//        Pageable pageable = requestDTO.getPageable(Sort.by("pnum").descending());
//
//        BooleanBuilder booleanBuilder = getSearch(requestDTO);
//
//        Page<Product> result = productRepository.findAll(booleanBuilder, pageable);
//
//        Function<Product, ProductDTO> fn = (product -> modelMapper.map(product, ProductDTO.class));
//
//        return new PageResultDTO<>(result, fn);
//    }

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
        IntStream.rangeClosed(0, productImageList.size()).forEach(i -> {
            productImageList.get(i).changeProductImage(dto.getImageDTOList().get(i));
        });

//        Map<String, Object> entityMap = dtoToEntity(dto);
//        Product product = (Product) entityMap.get("product");
//        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

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

        imageRepository.deleteByPnum(pnum); // 이미지를 삭제한다.
        productRepository.deleteById(pnum); // 상품을 삭제한다.

    }

//    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
//
//        String type = requestDTO.getType();
//
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//
//        QProduct qProduct = QProduct.product;
//
//        String keyword = requestDTO.getKeyword();
//
//        BooleanExpression expression = qProduct.pnum.gt(0L);
//
//        booleanBuilder.and(expression);
//
//        if(type == null || type.trim().length() == 0){
//            return booleanBuilder;
//        }
//
//        BooleanBuilder conditionBuilder = new BooleanBuilder();
//
//        if(type.contains("n")){
//            conditionBuilder.or(qProduct.pname.contains(keyword));
//        }
//        if(type.contains("c")){
//            conditionBuilder.or(qProduct.pcat.contains(keyword));
//        }
//
//        booleanBuilder.and(conditionBuilder);
//
//        return booleanBuilder;

//    }

}