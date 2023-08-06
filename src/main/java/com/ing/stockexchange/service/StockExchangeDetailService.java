package com.ing.stockexchange.service;

import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.entity.StockExchangeDetail;
import com.ing.stockexchange.entity.StockExchangeDetailId;
import com.ing.stockexchange.exception.ResourceNotFoundException;
import com.ing.stockexchange.repository.StockExchangeDetailRepository;
import com.ing.stockexchange.repository.StockExchangeRepository;
import com.ing.stockexchange.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockExchangeDetailService {

    private final StockExchangeDetailRepository stockExchangeDetailRepository;
    private final StockExchangeRepository stockExchangeRepository;

    private final StockRepository stockRepository;

    @Autowired
    public StockExchangeDetailService(StockExchangeDetailRepository stockExchangeDetailRepository, StockExchangeRepository stockExchangeRepository, StockRepository stockRepository) {
        this.stockExchangeDetailRepository = stockExchangeDetailRepository;
        this.stockExchangeRepository = stockExchangeRepository;
        this.stockRepository = stockRepository;
    }


    public StockExchangeDetail findStockExchangeDetailByExchangeName(String stockExchangeName) {
        return stockExchangeDetailRepository.findByStockExchangeName(stockExchangeName);
    }

    public void addStockToExchange(long stockId, long exchangeId, String stockName, String stockExchangeName) {

        // Check if the record already exists
        if (stockExchangeDetailRepository.existsById(new StockExchangeDetailId(stockId, exchangeId))) {
            throw new DuplicateKeyException("Duplicated Record");
        }

        StockExchangeDetailId id = new StockExchangeDetailId();
        id.setStockId(stockId);
        id.setExchangeId(exchangeId);

        StockExchangeDetail stockExchangeDetail = new StockExchangeDetail();
        stockExchangeDetail.setId(id);
        stockExchangeDetail.setStockName(stockName);
        stockExchangeDetail.setStockExchangeName(stockExchangeName);
        stockExchangeDetail.setStartDate(new Timestamp(System.currentTimeMillis()));


            stockExchangeDetailRepository.save(stockExchangeDetail);
            // Check the count of stocks for the given exchange
            long stockCount = stockExchangeDetailRepository.countByIdExchangeId(exchangeId);

            // Fetch the stock exchange entity
            Optional<StockExchange> optionalStockExchange = stockExchangeRepository.findById(exchangeId);
            if (optionalStockExchange.isPresent()) {
                StockExchange stockExchange = optionalStockExchange.get();

                // Set the liveInMarket field to true if the stock count exceeds 5
                boolean isLiveInMarket = false;
                if(stockCount>=5){
                    isLiveInMarket = true;
                }

                stockExchange.setLiveInMarket(isLiveInMarket);

                stockExchangeRepository.save(stockExchange);
        }

    }

    public List<Stock> getStocksByStockExchange(Long exchangeId) {
        List<StockExchangeDetail> stockExchangeDetails = stockExchangeDetailRepository.findByIdExchangeId(exchangeId);

        List<Stock> stocks = new ArrayList<>();
        for (StockExchangeDetail detail : stockExchangeDetails) {
            Stock stock = stockRepository.findByName(detail.getStockName());
            if (stock != null) {
                stocks.add(stock);
            }
        }

        return stocks;
    }

    @Transactional
    public void removeStockFromExchange(String stockExchangeName, String stockName) {
        stockExchangeDetailRepository.deleteByStockExchangeNameAndStockName(stockExchangeName, stockName);
    }

    @Transactional
    public void removeStockExchange(String stockExchangeName) {
        StockExchange stockExchange = stockExchangeRepository.findByName(stockExchangeName);
        if (stockExchange != null) {
            stockExchangeRepository.delete(stockExchange);
            stockExchangeDetailRepository.deleteByStockExchangeName(stockExchangeName);
        } else {
            throw new ResourceNotFoundException("Stock Exchange not found with name: " + stockExchangeName);
        }
    }

    public List<StockExchange> getAllStockExchanges() {
        return stockExchangeRepository.findAll();
    }




}