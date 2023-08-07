package com.ing.stockexchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePriceRequest {
    private String stockName;
    private BigDecimal newPrice;

}