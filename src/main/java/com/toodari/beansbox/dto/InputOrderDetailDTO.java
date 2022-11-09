package com.toodari.beansbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InputOrderDetailDTO {
    private Long odnum;

    private Long odquantity;

}
