package com.ing.stockexchange.repository;

import com.ing.stockexchange.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByName(String name);

    boolean existsByNameOrDescription(String name, String description);

}