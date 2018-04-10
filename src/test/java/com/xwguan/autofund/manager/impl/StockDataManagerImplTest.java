package com.xwguan.autofund.manager.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.manager.api.StockDataManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class StockDataManagerImplTest {
    @Autowired
    private StockDataManager stockDataManager;

//    @Test
    public void testListAShare() throws IOException {
        List<Stock> listAShare = stockDataManager.listAShare();
        System.out.println(listAShare);
    }
    
    @Test
    public void testGetStock() {
        try {
            Stock stock = stockDataManager.getStock("002329.SZ", LocalDate.of(1990, 11, 19),
                LocalDate.of(2017, 12, 28));
            System.out.println(stock);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetRealTimeStockData() {
        try {
            RealTimeStockData realTimeStockData = stockDataManager.getRealTimeStockData("000001.SH");
            System.out.println(realTimeStockData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
