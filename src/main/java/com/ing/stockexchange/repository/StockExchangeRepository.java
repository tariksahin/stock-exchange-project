package com.ing.stockexchange.repository;


import com.ing.stockexchange.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchange, Long> {
    StockExchange findByName(String name);

    boolean existsByNameOrDescription(String name, String description);
}
