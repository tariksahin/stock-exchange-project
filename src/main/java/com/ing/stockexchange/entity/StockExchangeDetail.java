package com.ing.stockexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STOCK_EXCHANGE_DETAIL")
public class StockExchangeDetail {

    @EmbeddedId
    private StockExchangeDetailId id;
    private String stockName;
    private String stockExchangeName;
    private Timestamp startDate;

}
