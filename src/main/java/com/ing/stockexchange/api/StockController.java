package com.ing.stockexchange.api;


import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.mapper.StockMapper;
import com.ing.stockexchange.service.StockExchangeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private final StockExchangeServiceImpl stockExchangeServiceImpl;


    public StockController(StockExchangeServiceImpl stockExchangeServiceImpl) {
        this.stockExchangeServiceImpl = stockExchangeServiceImpl;
    }

    // Create a new stock
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<StockDTO> addStock(@RequestBody StockDTO stockDTO) {
        Stock createdStock = stockExchangeServiceImpl.addStock(stockDTO);
        StockDTO responseDTO = StockMapper.INSTANCE.toDTO(createdStock);
        return ResponseEntity.ok(responseDTO);
    }

    // Create a new stock exchange
    @PostMapping(value = "/exchange", consumes = "application/json", produces = "application/json")
    public ResponseEntity<StockExchangeDTO> addStockExchange(@RequestBody StockExchangeDTO stockExchangeDTO) {
        StockExchange createdStockExchange = stockExchangeServiceImpl.addStockExchange(stockExchangeDTO);
        StockExchangeDTO responseDTO = StockMapper.INSTANCE.toDTO(createdStockExchange);
        return ResponseEntity.ok(responseDTO);
    }


    // Update stock price
    @PutMapping("/{name}")
    public ResponseEntity<Void> updateCurrentPrice(@PathVariable String name, @RequestBody BigDecimal newPrice) {
        stockExchangeServiceImpl.updateCurrentPrice(name, newPrice);
        return ResponseEntity.noContent().build();
    }

    //List of stocks
    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockExchangeServiceImpl.getAllStocks();
        return ResponseEntity.ok(stocks);
    }



    //Delete stock from system
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteStockByName(@PathVariable String name){

        stockExchangeServiceImpl.deleteStockByName(name);
        return ResponseEntity.noContent().build();
    }


}
