package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HistoryDTO {
    private String pname;
    private Long pcost;
    private Long pprice;
    private int pactive;

    private Long odquantity;

    @Builder.Default
    private List<ProductImageDTO> imageDTOList = new ArrayList<>();
}
