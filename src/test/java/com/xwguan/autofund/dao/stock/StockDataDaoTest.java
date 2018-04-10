package com.xwguan.autofund.dao.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.stock.StockData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class StockDataDaoTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockDataDao stockDataDao;
    
//    @Test
    public void testListAllStockData() {
        List<StockData> stockDatalList = stockDataDao.listAllStockData();
        logger.info("all_stockDatalList={}", stockDatalList);
    }

//    @Test
    public void testListStockDataByStockId() {
        List<StockData> stockDatalList = stockDataDao.listStockDataByStockId(1, null);
        logger.info("id_stockDatalList={}", stockDatalList);
    }

//    @Test
    public void testListStockDataByDateRange() {
        List<StockData> stockDatalList = stockDataDao.listStockDataByDateRange(1, LocalDate.parse("2017-10-17"),
            LocalDate.parse("2017-10-18"), null);
        logger.info("range_stockDatalList={}", stockDatalList);
    }

//    @Test
    public void testInsertStockData() {
        List<StockData> testStockDataList = new ArrayList<>();
        testStockDataList
            .add(new StockData(2, LocalDate.now(), 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, null, null, null));
        testStockDataList.add(new StockData(2, LocalDate.now().minusDays(1), 1.1, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
            9.0, null, null, null));
        // int success = stockDataDao.insertStockData(testStockDataList);
        // logger.info("insert_success={}", success);
    }

    @Test
    public void testUpdateStockData() {
    }

//    @Test
    public void testDeleteStockDataByStockIdAndDate() {
    }

//    @Test
    public void testDeleteStockDataByStockId() {
        long success = stockDataDao.deleteStockDataByStockId(1);
        logger.info("delete_success={}", success);
    }

//    @Test
    public void testDeleteStockDataBatchByStockId() {
        long success = stockDataDao.deleteStockDataByStockId(1);
        logger.info("delete_success={}", success);
    }

//    @Test
    public void testGetLatestDate() {
        logger.info("latest_date={}", stockDataDao.getLatestDate(2));
    }
    
//    @Test
    public void testListDateByDateRange() {
        System.out.println(stockDataDao.listDateByDateRange(1, LocalDate.parse("2017-10-13"), LocalDate.parse("2017-10-31"), null));
    }
}
