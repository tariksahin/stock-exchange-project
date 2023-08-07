package com.ing.stockexchange.api;


import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.service.StockExchangeDetailService;
import com.ing.stockexchange.service.StockExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/stock-exchange")
public class StockExchangeController {

    private final StockExchangeServiceImpl stockService;
    private final StockExchangeDetailService stockExchangeDetailService;

    @Autowired
    public StockExchangeController(StockExchangeServiceImpl stockService, StockExchangeDetailService stockExchangeDetailService) {
        this.stockService = stockService;
        this.stockExchangeDetailService = stockExchangeDetailService;
    }

    @PostMapping
    public ResponseEntity<String> addStockToExchange(@RequestParam String stockName, @RequestParam String stockExchangeName) {
        Stock stock = stockService.findStockByName(stockName);
        StockExchange stockExchange = stockService.findStockExchangeByName(stockExchangeName);

        if (stock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock with name '" + stockName + "' not found.");
        }

        if (stockExchange == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock Exchange with name '" + stockExchangeName + "' not found.");
        }

        try {
            stockExchangeDetailService.addStockToExchange(stock.getStockId(), stockExchange.getExchangeId(), stockName, stockExchangeName);
            return ResponseEntity.ok("Stock added to Stock Exchange successfully.");
        } catch (DuplicateKeyException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: Stock with name " + stockName + " already exists in the exchange " +stockExchangeName);
        }


    }

    @GetMapping("/{stockExchangeName}")
    public ResponseEntity<List<Stock>> getStocksByStockExchange(@PathVariable String stockExchangeName) {
        StockExchange stockExchange = stockService.findStockExchangeByName(stockExchangeName);

        if (stockExchange == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<Stock> stocks = stockExchangeDetailService.getStocksByStockExchange(stockExchange.getExchangeId());
        return ResponseEntity.ok(stocks);
    }

    @DeleteMapping("/{stockExchangeName}/{stockName}")
    public ResponseEntity<Void> removeStockFromExchange(@PathVariable String stockExchangeName, @PathVariable String stockName) {
        stockExchangeDetailService.removeStockFromExchange(stockExchangeName, stockName);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{stockExchangeName}")
    public ResponseEntity<Void> removeStockExchange(@PathVariable String stockExchangeName) {
        stockExchangeDetailService.removeStockExchange(stockExchangeName);
        return ResponseEntity.ok().build();
    }

    //List of stocks
    @GetMapping
    public ResponseEntity<List<StockExchange>> getAllStocks() {
        List<StockExchange> stocks = stockExchangeDetailService.getAllStockExchanges();
        return ResponseEntity.ok(stocks);
    }

}
