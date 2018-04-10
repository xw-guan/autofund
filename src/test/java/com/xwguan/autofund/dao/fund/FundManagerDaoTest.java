package com.xwguan.autofund.dao.fund;

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

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.fund.FundManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundManagerDaoTest {

    @Autowired
    private FundManagerDao fundManagerDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Page page = new Page(1, 2);

     @Test
    public void testGetFundManagerById() {
        logger.info("====================\nfundManager={}", fundManagerDao.getFundManagerById(1));
    }

    // @Test
    public void testGetFundManagerByCode() {
        logger.info("====================\nfundManager={}", fundManagerDao.getFundManagerByCode("123456"));
    }

    // @Test
    public void testListAllFundManager() {
        logger.info("====================\nall fund company={}", fundManagerDao.listAllFundManager(page));
    }

    // @Test
    public void testListFundManagerBySearchField() {
        logger.info("====================\nfundManager={}", fundManagerDao.listFundManagerBySearchField("12345", page));
        logger.info("====================\nfundManager={}", fundManagerDao.listFundManagerBySearchField("test", page));
    }

    // @Test
    public void testListFundManagerByCompanyCode() {
        logger.info("====================\nfundManager={}", fundManagerDao.listFundManagerByCompanyCode("000001", page));
        logger.info("====================\nfundManager={}", fundManagerDao.listFundManagerByCompanyCode("000002", page));
    }

    // @Test
    public void testCountFundManager() {
        logger.info("====================\nfundManagercnt={}", fundManagerDao.countFundManager());
    }

    // @Test
    public void testInsertFundManager() {
        List<FundManager> fundmanagerList = new ArrayList<FundManager>();
        fundmanagerList.add(new FundManager("123457", "name2", "000001", "company1", "aaaa", "aaaa", "10", "123"));
        fundmanagerList.add(new FundManager("123458", "name3", "000002", "company2", "aaaa", "aaaa", "10", "123"));
        fundmanagerList.add(new FundManager("123459", "name4", "000002", "company2", "aaaa", "aaaa", "10", "123"));
        fundManagerDao.insertFundManager(fundmanagerList);
    }

    // @Test
    public void testInsertAndGetId() {
        FundManager fundManager = new FundManager("123456", "name1", "000001", "company1", "aaaa", "aaaa", "10", "123");
        fundManagerDao.insertAndGetId(fundManager);
        logger.info("===============================id={}", fundManager.getId());
    }

    // @Test
    public void testUpdateFundManager() {
        FundManager fundManager = new FundManager(1, "000000", "name1", "000001", "company1", "aaaa", "aaaa", "10",
            "123");
        fundManagerDao.updateFundManager(fundManager);
    }

    // @Test
    public void testDeleteFundManagerById() {
        fundManagerDao.deleteFundManagerById(10);
    }

    // @Test
    public void testDeleteFundManagerBatchById() {
        fundManagerDao.deleteFundManagerBatchById(Arrays.asList(1, 2, 3));
    }

}
