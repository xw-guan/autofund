package com.xwguan.autofund.service.mapper.stock;

import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.manager.impl.NeteaseCsvStockData;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:03+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class NeteaseStockMapperImpl implements NeteaseStockMapper {

    @Override
    public StockData toStockData(NeteaseCsvStockData neteaseCsvStockData) {
        if ( neteaseCsvStockData == null ) {
            return null;
        }

        StockData stockData = new StockData();

        if ( neteaseCsvStockData.getDate() != null ) {
            stockData.setDate( java.time.LocalDate.parse( neteaseCsvStockData.getDate() ) );
        }
        stockData.setClose( neteaseCsvStockData.getClose() );
        stockData.setHigh( neteaseCsvStockData.getHigh() );
        stockData.setLow( neteaseCsvStockData.getLow() );
        stockData.setOpen( neteaseCsvStockData.getOpen() );
        stockData.setPclose( neteaseCsvStockData.getPclose() );
        stockData.setChg( neteaseCsvStockData.getChg() );
        stockData.setPchg( neteaseCsvStockData.getPchg() );
        stockData.setVoturnover( neteaseCsvStockData.getVoturnover() );
        stockData.setVaturnover( neteaseCsvStockData.getVaturnover() );
        stockData.setTurnover( neteaseCsvStockData.getTurnover() );
        stockData.setTcap( neteaseCsvStockData.getTcap() );
        stockData.setMcap( neteaseCsvStockData.getMcap() );

        return stockData;
    }
}
