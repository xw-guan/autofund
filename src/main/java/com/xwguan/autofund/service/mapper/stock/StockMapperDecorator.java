package com.xwguan.autofund.service.mapper.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xwguan.autofund.dto.stock.LatestStockDto;
import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.service.util.Comparators;

public abstract class StockMapperDecorator implements StockMapper {

    @Autowired
    @Qualifier("delegate")
    private StockMapper delegate;

    @Override
    public LatestStockDto toLatestStockDto(Stock stock) {
        LatestStockDto latestStockDto = delegate.toLatestStockDto(stock);
        if (latestStockDto == null) {
            return null;
        }
        StockData latestStockData = stock.getStockDataList().parallelStream()
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        MA latestMA = stock.getMaList().parallelStream()
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        latestStockDto.setLatestStockData(latestStockData);
        latestStockDto.setLatestMA(latestMA);
        return latestStockDto;
    }

    /**
     * 如果不显示地写出这个方法, 会按照delegate的toLatestStockDtos运行(注意不是重载!!!)
     */
    @Override
    public List<LatestStockDto> toLatestStockDtos(List<Stock> stocks) {
        if ( stocks == null ) {
            return null;
        }
        List<LatestStockDto> list = new ArrayList<LatestStockDto>( stocks.size() );
        for ( Stock stock : stocks ) {
            list.add( toLatestStockDto( stock ) );
        }
        return list;
    }

    
}
