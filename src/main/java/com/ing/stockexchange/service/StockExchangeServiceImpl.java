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

        String stockName = stockDTO.getName();
        String description = stockDTO.getDescription();

        // Check if a stock with the same name or description already exists
        if (stockRepository.existsByNameOrDescription(stockName, description)) {
            throw new ResourceAlreadyExistsException("Stock with the same name or description already exists.");
        }

        Stock stock = StockMapper.INSTANCE.toEntity(stockDTO);

        Date date = new Date();
        stock.setLastUpdate(new Timestamp(date.getTime()));


        return stockRepository.save(stock);
    }

    public StockExchange addStockExchange(StockExchangeDTO stockExchangeDTO) {

        String stockExchangeName = stockExchangeDTO.getName();
        String description = stockExchangeDTO.getDescription();
        // Check if a stock with the same name or description already exists
        if (stockExchangeRepository.existsByNameOrDescription(stockExchangeName, description)) {
            throw new ResourceAlreadyExistsException("Stock exchange with the same name or description already exists.");
        }

        StockExchange stockExchange = StockMapper.INSTANCE.toEntity(stockExchangeDTO);
        stockExchange.setLiveInMarket(false);

        return stockExchangeRepository.save(stockExchange);
    }

    public void deleteStockByName(String stockName) {

        Stock stock = stockRepository.findByName(stockName);
        if (stock != null) {
            stockRepository.delete(stock);
        } else {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
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


    public Stock findStockByName(String stockName) {

        Stock stock = stockRepository.findByName(stockName);
        if (stock == null) {
            throw new ResourceNotFoundException("Stock not found with name: " + stockName);
        }
        return stock;
    }

    public StockExchange findStockExchangeByName(String stockName) {

        StockExchange stockExchange = stockExchangeRepository.findByName(stockName);
        if (stockExchange == null) {
            throw new ResourceNotFoundException("Stock Exchange not found with name: " + stockName);
        }
        return stockExchange;
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }


}