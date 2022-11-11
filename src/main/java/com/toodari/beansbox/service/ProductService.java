package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.dto.ProductImageDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {
    Long register(ProductDTO dto);

    ProductDTO read(Long pnum);

    Long modify(ProductDTO dto);

    Long copy(ProductDTO dto);

    void remove(Long pnum);

    void removeWithImages(Long pnum);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages){
        ProductDTO productDTO = ProductDTO.builder()
                .pnum(product.getPnum())
                .pname(product.getPname())
                .pcat(product.getPcat())
                .pquantity(product.getPquantity())
                .pcost(product.getPcost())
                .pprice(product.getPprice())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .build();


        List<ProductImageDTO> productImageDTOList = productImages.stream().map(productImage -> {
            return ProductImageDTO.builder()
                    .imgname(productImage.getImgname())
                    .imgpath(productImage.getImgpath())
                    .imguuid(productImage.getImguuid())
                    .build();
        }).collect(Collectors.toList());

        productDTO.setImageDTOList(productImageDTOList);

        return productDTO;
    }

    default Map<String, Object> dtoToEntity(ProductDTO productDTO){

        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .pnum(productDTO.getPnum())
                .pname(productDTO.getPname())
                .pcat(productDTO.getPcat())
                .pquantity(productDTO.getPquantity())
                .pcost(productDTO.getPcost())
                .pprice(productDTO.getPprice())
                .build();

        entityMap.put("product", product);

        List<ProductImageDTO> imageDTOList = productDTO.getImageDTOList();

        if(imageDTOList != null && imageDTOList.size() > 0){
            List<ProductImage> productImageList = imageDTOList.stream().map(productImageDTO -> {

                ProductImage productImage = ProductImage.builder()
                        .imgpath(productImageDTO.getImgpath())
                        .imgname(productImageDTO.getImgname())
                        .imguuid(productImageDTO.getImguuid())
                        .product(product)
                        .build();
                return productImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", productImageList);
        }

        return entityMap;
    }

}
