package com.xwguan.autofund.service.mapper.stock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.manager.impl.NeteaseCsvStockData;

@Mapper
public interface NeteaseStockMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "stockId", ignore = true)
    })
    StockData toStockData(NeteaseCsvStockData neteaseCsvStockData);
}
