package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OutputOrderDetailDTO {
    private Long odnum;

    private Long odquantity;

    private Long pnum;

    private Long onum;

}
