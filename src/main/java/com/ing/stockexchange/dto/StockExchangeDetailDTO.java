package com.ing.stockexchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockExchangeDetailDTO {


    @JsonProperty("stockName")
    private String stockName;
    @JsonProperty("stockExchangeName")
    private String stockExchangeName;
}
