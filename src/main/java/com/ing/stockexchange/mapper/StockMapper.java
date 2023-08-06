package com.ing.stockexchange.mapper;


import com.ing.stockexchange.dto.StockDTO;
import com.ing.stockexchange.dto.StockExchangeDTO;
import com.ing.stockexchange.dto.StockExchangeDetailDTO;
import com.ing.stockexchange.entity.Stock;
import com.ing.stockexchange.entity.StockExchange;
import com.ing.stockexchange.entity.StockExchangeDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StockMapper {
    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);


    Stock toEntity(StockDTO stockDTO);
    StockDTO toDTO(Stock stock);

    StockExchange toEntity(StockExchangeDTO stockExchangeDTO);
    StockExchangeDTO toDTO(StockExchange stockExchange);

    StockExchangeDetail toEntity(StockExchangeDetailDTO stockExchangeDetailDTO);
    StockExchangeDetailDTO toDTO(StockExchangeDetail stockExchangeDetail);
}