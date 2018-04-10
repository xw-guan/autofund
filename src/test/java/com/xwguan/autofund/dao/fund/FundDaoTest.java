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

import com.xwguan.autofund.entity.fund.Fund;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundDaoTest {

    @Autowired
    private FundDao fundDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

     @Test
    public void testGetCodeById() {
        logger.info("====================\nGetCodeById={}", fundDao.getCodeById(10000));
    }

//    @Test
    public void testGetFundById() {
        logger.info("====================\nfund={}", fundDao.getFundById(1, false, false, null));
        logger.info("====================\nfund={}", fundDao.getFundById(1, true, false, null));
        logger.info("====================\nfund={}", fundDao.getFundById(1, false, true, null));
        logger.info("====================\nfund={}", fundDao.getFundById(1, true, true, null));
    }

//     @Test
    public void testGetFundByCode() {
        logger.info("====================\nfund={}", fundDao.getFundByCode("000009", false, false, null));
        logger.info("====================\nfund={}", fundDao.getFundByCode("000009", true, false, null));
        logger.info("====================\nfund={}", fundDao.getFundByCode("000009", false, true, null));
        logger.info("====================\nfund={}", fundDao.getFundByCode("000009", true, true, null));
    }

    // @Test
    public void testListAllFund() {
        logger.info("====================\nall fund={}", fundDao.listAllFund(false, false));
        List<Fund> listAllFund = fundDao.listAllFund(true, true);
        for (Fund f : listAllFund) {
            logger.info("Fund: {}", f);
        }
    }

    // @Test
    public void testListFundBySearchField() {
        String[] searchFields = { "00000", "000009", "测试", "cs" };
        for (String s : searchFields) {
            List<Fund> listFundBySearchField = fundDao.listFundBySearchField(s, false, false, null);
            logger.info("-------------------------\nsearch field: {}, result:", s);
            for (Fund f : listFundBySearchField) {
                logger.info("Fund: {}", f);
            }
        }
    }

    // @Test
    public void testListFundByType() {
        List<Fund> listFundBySearchField = fundDao.listFundByType("测试型", false, false, null);
        for (Fund f : listFundBySearchField) {
            logger.info("Fund: {}", f);
        }
    }

    // @Test
    public void testInsertFund() {
        List<Fund> fundList = new ArrayList<Fund>();
        fundList.add(new Fund("000001", "测试", "CS", "CESHI", "测试型"));
        fundList.add(new Fund("000002", "测试二", "CSE", "CESHIER", "测试型"));
        fundList.add(new Fund("000003", "测试三", "CSS", "CESHISAN", "测试型2"));
        fundList.add(new Fund("100000", "也是测试", "YSCS", "YESHICESHI", "测试型2"));
        fundDao.insertFund(fundList);
    }

    // @Test
    public void testInsertAndGetId() {
        Fund fund = new Fund("000004", "测试四", "CSS", "CESHISI", "测试型");
        fundDao.insertAndGetId(fund);
        logger.info("====================\nfund={}", fund);
    }

    // @Test
    public void testUpdateFund() {
        Fund fund = new Fund(1, "000009", "测试", "CS", "CESHI", "测试型");
        fundDao.updateFund(fund);
    }

//     @Test
    public void testUpdateFundBatch() {
        List<Fund> fundList = new ArrayList<Fund>();
        fundList.add(new Fund(5, "000004", "测试四", "CSS", "CESHISI", "测试型"));
        fundList.add(new Fund(7, "000001", "测试一", "CSY", "CESHIYI", "测试型"));
        fundList.add(new Fund(6, "000010", "测试六", "CSL", "CESHILIU", "测试型"));
//        fundDao.updateFundBatch(fundList);
    }
    
//     @Test
    public void testUpdateFundBatchByFundCode() {
        List<Fund> fundList = new ArrayList<Fund>();
        fundList.add(new Fund("000004", "测试四", "CSS", "CESHISI", "测试型2"));
        fundList.add(new Fund("000001", "测试一", "CSY", "CESHIYI", "测试型2"));
        fundList.add(new Fund("000010", "测试六", "CSL", "CESHILIU", "测试型2"));
//        fundDao.updateFundBatchByFundCode(fundList);
    }
    
    // @Test
    public void testDeleteFundByCode() {
        fundDao.deleteFundByCode("000003");
    }

    // @Test
    public void testDeleteFundById() {
        fundDao.deleteFundById(2);
    }

    // @Test
    public void testDeleteFundBatchById() {
        fundDao.deleteFundBatchById(Arrays.asList(1, 2, 3, 4, 5));
    }

}
