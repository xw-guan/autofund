package com.xwguan.autofund.service.mapper.stock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.stock.RealTimeMA;
import com.xwguan.autofund.entity.stock.RealTimeStockData;

@Mapper
public interface RealTimeMAMapper {

    /**
     * map基本信息, ignore数据
     */
    @Mappings({
        @Mapping(target = "ma5", ignore = true),
        @Mapping(target = "ma10", ignore = true),
        @Mapping(target = "ma20", ignore = true),
        @Mapping(target = "ma30", ignore = true),
        @Mapping(target = "ma60", ignore = true),
        @Mapping(target = "ma120", ignore = true),
        @Mapping(target = "ma250", ignore = true)
    })
    RealTimeMA toRealTimeMA(RealTimeStockData realTimeStockData);
}
