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
public class OrdersDTO {
    private Long onum;
    private String ocat;

    private int odcount;

    private LocalDateTime regDate, modDate;

    private Long mnum;
    private String mname;
}
