package com.xwguan.autofund.dao.fund;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.fund.FundHistory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundHistoryDaoTest {

    @Autowired
    private FundHistoryDao fundHistoryDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Test
    public void testListAllFundHistory() {
        logger.info("====================\nall fundHistory={}", fundHistoryDao.listAllFundHistory());
    }

//    @Test
    public void testListFundHistoryByFundId() {
        logger.info("====================\nfundHistory={}", fundHistoryDao.listFundHistoryByFundId(1, null));
    }

//     @Test
    public void testListFundHistoryByDateRange() {
        logger.info("====================\nfundHistory={}",
            fundHistoryDao.listFundHistoryByDateRange(1, LocalDate.now().minus(5, ChronoUnit.DAYS),
                LocalDate.now().minus(3, ChronoUnit.DAYS), null));
    }

//     @Test
    public void testListFundHistoryDaysBefore() {
        logger.info("====================\nfundHistory={}",
            fundHistoryDao.listFundHistoryDaysBefore(1, LocalDate.now(), 3, null));
    }
    
     @Test
    public void testGetFundHistoryDaysBefore() {
        logger.info("====================\nfundHistory={}",
            fundHistoryDao.getFundHistoryDaysBefore(7352, LocalDate.of(2017, 12, 21), 3));
    }

//     @Test
    public void testGetLatestDate() {
        logger.info("====================\nLatestDate={}", fundHistoryDao.getLatestDate(1));
    }

//     @Test
    public void testGetLatestFundHistory() {
        logger.info("====================\nfundHistory={}", fundHistoryDao.getLatestFundHistory(1));
    }

//     @Test
    public void testInsertFundHistory() {
        List<FundHistory> fundHistoryList = new ArrayList<FundHistory>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 5; i++) {
            fundHistoryList.add(new FundHistory(1, date, 1.0, 1.0, 0.0, null));
            date = date.minus(1, ChronoUnit.DAYS);
        }
        fundHistoryList.add(new FundHistory(2, date, 1.0, 1.0, 0.0, null));
        fundHistoryDao.insertFundHistory(fundHistoryList);
    }

//     @Test
    public void testUpdateFundHistory() {
        FundHistory fundHistory = new FundHistory(1l, 1, LocalDate.of(2017, 12, 17), 2.0, 2.0, 100.0, null);
        fundHistoryDao.updateFundHistory(fundHistory);
    }

//     @Test
    public void testDeleteFundHistoryByFundIdAndDate() {
        fundHistoryDao.deleteFundHistoryByFundIdAndDate(1, LocalDate.now());
    }

//     @Test
    public void testDeleteFundHistoryByFundId() {
        fundHistoryDao.deleteFundHistoryByFundId(2);
    }

//     @Test
    public void testDeleteFundHistoryBatchById() {
        fundHistoryDao.deleteFundHistoryBatchByFundId(Arrays.asList(1, 2));
    }

}
