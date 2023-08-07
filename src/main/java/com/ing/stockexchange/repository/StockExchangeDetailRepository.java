package com.ing.stockexchange.repository;

import com.ing.stockexchange.entity.StockExchangeDetail;
import com.ing.stockexchange.entity.StockExchangeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockExchangeDetailRepository extends JpaRepository<StockExchangeDetail, StockExchangeDetailId> {

    void deleteByStockExchangeNameAndStockName(String stockExchangeName, String stockName);

    void deleteByStockExchangeName(String stockExchangeName);

    List<StockExchangeDetail> findByIdExchangeId(long exchangeId);

    StockExchangeDetail findByStockExchangeName(String stockExchangeName);

    long countByIdExchangeId(long exchangeId);


}
