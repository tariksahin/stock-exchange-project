package com.ing.stockexchange.repository;

import com.ing.stockexchange.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByName(String name);

    boolean existsByNameOrDescription(String name, String description);

    @Modifying
    @Query("UPDATE Stock s SET s.currentPrice = :newPrice, s.lastUpdate = CURRENT_TIMESTAMP WHERE s.name = :name")
    void updateStockPriceByName(@Param("name") String name, @Param("newPrice") BigDecimal newPrice);


}