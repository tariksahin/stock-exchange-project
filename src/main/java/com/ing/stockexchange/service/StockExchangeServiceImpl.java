package com.ing.stockexchange.service;


import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.exception.ResourceAlreadyExistsException;
import com.ing.stockexchange.exception.ResourceNotFoundException;
import com.ing.stockexchange.mapper.StockMapper;
import com.ing.stockexchange.repository.StockExchangeRepository;
import com.ing.stockexchange.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockExchangeServiceImpl {


    private final StockRepository stockRepository;
    private final StockExchangeRepository stockExchangeRepository;


    public Stock addStock(StockDTO stockDTO) {

        String name = stockDTO.getName();
        String description = stockDTO.getDescription();

        // Check if a stock with the same name or description already exists
        if (stockRepository.existsByNameOrDescription(name, description)) {
            throw new ResourceAlreadyExistsException("Stock with the same name or description already exists.");
        }

        Stock stock = StockMapper.INSTANCE.toEntity(stockDTO);

        Date date = new Date();
        stock.setLastUpdate(new Timestamp(date.getTime()));


        return stockRepository.save(stock);
    }

    public StockExchange addStockExchange(StockExchangeDTO stockExchangeDTO) {

        StockExchange stockExchange = StockMapper.INSTANCE.toEntity(stockExchangeDTO);
        stockExchange.setLiveInMarket(false);

       return stockExchangeRepository.save(stockExchange);
    }

    public void deleteStockByName(String name) {

        Stock stock = stockRepository.findByName(name);
        if (stock != null) {
            stockRepository.delete(stock);
        } else {
            throw new ResourceNotFoundException("Stock not found with name: " + name);
        }
    }

    @Transactional
    public void updateCurrentPrice(String stockName, BigDecimal newPrice) {
        Stock stock = stockRepository.findByName(stockName);
        if (stock != null) {
            stock.setCurrentPrice(newPrice);
            stock.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            stockRepository.save(stock);
        } else {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
        }
    }


    public Stock findStockByName(String name) {

        return stockRepository.findByName(name);
    }

    public StockExchange findStockExchangeByName(String name) {

        return stockExchangeRepository.findByName(name);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }



}