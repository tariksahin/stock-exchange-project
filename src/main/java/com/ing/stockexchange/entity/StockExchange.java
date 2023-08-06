package com.ing.stockexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "STOCK_EXCHANGE")
@SequenceGenerator(name = "stock_exchange_sequence", sequenceName = "stock_exchange_seq", allocationSize=1)
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_exchange_sequence")
    private long exchangeId;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String description;
    private Boolean liveInMarket;

}
