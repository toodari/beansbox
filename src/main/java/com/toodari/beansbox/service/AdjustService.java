package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.*;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.ProductImage;

import java.util.List;
import java.util.stream.Collectors;

public interface AdjustService {

    public Long register(AdjustOrdersDTO adjustOrdersDTO, List<Long> pnum, List<Long> pquantity);

    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    public List<ProductDTO> getChkList(List<String> pnumList);

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
}
