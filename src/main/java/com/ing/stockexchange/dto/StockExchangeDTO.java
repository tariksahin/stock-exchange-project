package com.ing.stockexchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockExchangeDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
}
