package com.xwguan.autofund.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.dto.stock.StockUpdateState;
import com.xwguan.autofund.entity.stock.RealTimeMA;
import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.service.api.StockService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class StockServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;
    

//     @Test
    public void testGetStock() {
        Stock stock = stockService.getStock(1);
        System.out.println(stock);
        for (HistoryScopeEnum historyScope : HistoryScopeEnum.values()) {
            stock = stockService.getStock(1, historyScope, null);
            assertNotNull(stock);
            switch (historyScope) {
            case FULL:
                assertTrue(stock.getStockDataList().size() > 1);
                assertTrue(stock.getMaList().size() > 1);
                break;
            case LATEST:
                assertTrue(stock.getStockDataList().size() == 1);
                assertTrue(stock.getMaList().size() == 1);
                break;
            default:
                assertTrue(stock.getStockDataList().size() == 0);
                assertTrue(stock.getMaList().size() == 0);
                break;
            }
            stock = stockService.getStock("000001.SH", historyScope, null);
            assertNotNull(stock);
            switch (historyScope) {
            case FULL:
                assertTrue(stock.getStockDataList().size() > 1);
                assertTrue(stock.getMaList().size() > 1);
                break;
            case LATEST:
                assertTrue(stock.getStockDataList().size() == 1);
                assertTrue(stock.getMaList().size() == 1);
                break;
            default:
                assertTrue(stock.getStockDataList().size() == 0);
                assertTrue(stock.getMaList().size() == 0);
                break;
            }
        }
    }

//    @Test
    public void testListAllStock() {
        List<Stock> stockList = null;
        System.out.println(stockList);
        for (HistoryScopeEnum historyScope : HistoryScopeEnum.values()) {
            stockList = stockService.listAllStock(historyScope, null);
            assertTrue(stockList.size() > 0);
            for (Stock stock : stockList) {
                switch (historyScope) {
                case FULL:
                    assertTrue(stock.getStockDataList().size() > 1);
                    assertTrue(stock.getMaList().size() > 1);
                    break;
                case LATEST:
                    assertTrue(stock.getStockDataList().size() == 1);
                    assertTrue(stock.getMaList().size() == 1);
                    break;
                default:
                    assertTrue(stock.getStockDataList().size() == 0);
                    assertTrue(stock.getMaList().size() == 0);
                    break;
                }
            }
        }
    }
    
//    @Test
    public void count() throws IOException {
        int countStock = stockService.countStock();
        System.out.println("all: " + countStock);
        int countUpdateRequired = stockService.countUpdateRequired();
        System.out.println("updateRequered: " + countUpdateRequired);
    }
    
//    @Test
    public void testUpdateOrInsertStock() throws IOException {
        int cnt = stockService.updateStock();
        System.out.println(cnt);
    }
    
//    @Test
    public void testgetRealTime() throws IOException {
        int stockId = 1419;
        RealTimeStockData sd = stockService.getRealTimeStockData(stockId);
        System.out.println(sd);
        RealTimeMA ma = stockService.getRealTimeMA(stockId);
        System.out.println(ma);
    }
    

     @Test
    public void testUpdateAll() {
        try {
            boolean useMultiThread = false;
            LocalTime start = LocalTime.now();
            List<StockUpdateState> updateAllStates = stockService.updateStockData(useMultiThread);
            LocalTime end = LocalTime.now();
            long timeCost = start.until(end, ChronoUnit.SECONDS);
            logger.info("************Time Cost************ ={} s", timeCost);
            for (StockUpdateState s : updateAllStates) {
                logger.info("state={}", s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
