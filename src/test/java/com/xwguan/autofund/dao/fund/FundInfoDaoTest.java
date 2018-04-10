package com.xwguan.autofund.dao.fund;

import java.time.LocalDate;
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

import com.xwguan.autofund.entity.fund.FundInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundInfoDaoTest {

    @Autowired
    private FundInfoDao fundInfoDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

     @Test
    public void testGetFundInfoById() {
        logger.info("====================\nfundInfo={}", fundInfoDao.getFundInfoById(1));
    }

//     @Test
    public void testListFundInfoByFundId() {
        logger.info("====================\nfundInfo={}", fundInfoDao.getFundInfoByFundId(1));
    }

//    @Test
    public void testListAllFundInfo() {
        logger.info("====================\nall fundInfo={}", fundInfoDao.listAllFundInfo());
    }

//     @Test
    public void testInsertFundInfo() {
        List<FundInfo> fundInfoList = new ArrayList<FundInfo>();
        fundInfoList.add(new FundInfo(1, LocalDate.now(), "000001", "10 days", "00001"));
        fundInfoList.add(new FundInfo(2, LocalDate.now(), "000002", "10 days", "00001"));
        fundInfoDao.insertFundInfo(fundInfoList);
    }

//     @Test
    public void testUpdateFundInfo() {
        FundInfo fundInfo = new FundInfo(2, 2, LocalDate.now(), "000003", "20 days", "00002");
        fundInfoDao.updateFundInfo(fundInfo);
    }

//     @Test
    public void testDeleteFundInfoById() {
        fundInfoDao.deleteFundInfoById(1);
    }

//     @Test
    public void testDeleteFundInfoByFundId() {
        fundInfoDao.deleteFundInfoByFundId(2);
    }

    // @Test
    public void testDeleteFundInfoBatchById() {
        fundInfoDao.deleteFundInfoBatchByFundId(Arrays.asList(1, 2));
    }

}
