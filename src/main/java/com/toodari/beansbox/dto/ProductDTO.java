package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private Long pnum;
    private String pname;
    private String pcat;
    private Long pquantity;
    private Long pcost;
    private Long pprice;
    private int pactive;
    private LocalDateTime regDate, modDate;

    @Builder.Default
    private List<ProductImageDTO> imageDTOList = new ArrayList<>();
}