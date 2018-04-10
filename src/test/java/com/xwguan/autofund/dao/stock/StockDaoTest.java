package com.xwguan.autofund.dao.stock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.LoggerUtils;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.stock.Stock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class StockDaoTest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String[] testSymbols = { "000001", "000001.SH", "987654" };
    private String[] testNames = { "板指", "上证指数", "测试测试" };
    private Boolean[] testBooleans = { false, null, true };
    private Page page = new Page(1, 2);

    @Autowired
    private StockDao stockDao;

//     @Test
    public void testCountStock() throws Exception {
        logger.info("\n********************\ncount stock={}", stockDao.count());
    }

//     @Test
    public void testGetStockById() throws Exception {
        LoggerUtils.logPagedList(page, stockDao, "getStockById", 1, true); // ERROR
    }

//     @Test
    public void testListAllStock() throws Exception {
        List<Stock> stockList;
        for (Boolean b : testBooleans) {
            Page page = new Page();
            page.setTargetPage(2);
            stockList = stockDao.listAll(b, page);
            for (Stock s : stockList) {
                logger.info("includeData={}, listAllStock={}", b, s);
            }
        }
    }

    // @Test
    public void testListStockBySymbolOrName() throws Exception {
        for (Boolean b : testBooleans) {
            for (String symbol : testSymbols) {
                if (Boolean.TRUE.equals(b)) {
                    LoggerUtils.logPagedList(page, stockDao, "listStockBySymbolOrName", symbol, b);
                } else {
                    logger.info("listStockBySymbolOrName={}", stockDao.listBySymbolOrName(symbol, b, page));
                }
            }
            for (String name : testNames) {
                if (Boolean.TRUE.equals(b)) {
                    LoggerUtils.logPagedList(page, stockDao, "listStockBySymbolOrName", name, b);
                } else {
                    logger.info("listStockBySymbolOrName={}", stockDao.listBySymbolOrName(name, b, page));
                }
            }
        }
    }

    // @Test
    public void testlistLatest() throws Exception {
        LoggerUtils.logPagedList(page, stockDao, "listLatest", new Object[0]);
        // 若某支股票没有date数据, 则不满足mysql的查询条件, 不会被输出
    }

    // @Test
    public void testListIndex() throws Exception {
        LoggerUtils.logPagedList(page, stockDao, "listIndex", true);
    }

    // @Test
    public void testInsertAndGetId() {
        Stock stock = new Stock("000000.HZ", "测试股票", false);
        logger.info("" + stockDao.insertAndSetId(stock));
        logger.info("" + stock.getId());
    }

    // @Test
    public void testInsertStock() {
        List<Stock> testStockList = new ArrayList<Stock>();
        testStockList.add(new Stock("000003.sh", "测试指数", true, null, null));
        testStockList.add(new Stock("000004.sh", "测试股票", false, null, null));
        int success = stockDao.insertAndSetIdBatch(testStockList);
        logger.info("insert_success={}", success);
        logger.info("empty_success={}", stockDao.insertAndSetIdBatch(new ArrayList<Stock>()));
        logger.info("null_success={}", stockDao.insertAndSetIdBatch(null));
    }

    // @Test
    public void testUpdateStock() {
        Stock testStock = new Stock(14, "000003.sh", "测试指数更新", true, null, null);
        int success = stockDao.update(testStock);
        logger.info("update_success={}", success);
    }

    // @Test
    public void testDeleteStockById() {
        int success = stockDao.deleteById(14);
        logger.info("delete_success={}", success);
    }

    // @Test
    public void testDeleteStockBatchById() {
        int success = stockDao.deleteByIdListBatch(Arrays.asList(20L, 21L));
        logger.info("delete_success={}", success);
    }

    // @Test
    public void testDeleteStockBySymbol() {
        int success = stockDao.deleteStockBySymbol("000001.sh");
        logger.info("delete_success={}", success);
    }

}
