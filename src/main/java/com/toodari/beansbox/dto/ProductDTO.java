package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private Long p_num;
    private String p_name;
    private String p_cat;
    private Long p_quantity;
    private Long p_cost;
    private Long p_price;
    private int p_active;
    private LocalDateTime regDate, modDate;

}
