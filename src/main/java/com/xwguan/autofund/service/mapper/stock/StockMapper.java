package com.xwguan.autofund.service.mapper.stock;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.stock.LatestStockDto;
import com.xwguan.autofund.entity.stock.Stock;

@Mapper
@DecoratedWith(StockMapperDecorator.class)
public interface StockMapper {
    @Mappings({
        @Mapping(target = "id", source = "stock.id"),
        @Mapping(target = "latestStockData", ignore = true),
        @Mapping(target = "latestMA", ignore = true),
    })
    LatestStockDto toLatestStockDto(Stock stock);
    
    List<LatestStockDto> toLatestStockDtos(List<Stock> stocks);
}
