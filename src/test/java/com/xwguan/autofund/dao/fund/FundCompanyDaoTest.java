package com.xwguan.autofund.dao.fund;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.dto.common.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundCompanyDaoTest {

    @Autowired
    private FundCompanyDao fundCompanyDao;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private Page page = new Page(1, 2);
    
//    @Test
    public void testGetFundCompanyById() {
        logger.info("====================\nfundCompany={}", fundCompanyDao.getFundCompanyById(1));
    }

//    @Test
    public void testGetFundComapnyByCode() {
        logger.info("====================\nfundCompany={}", fundCompanyDao.getFundCompanyByCode("123456"));
    }

//    @Test
    public void testListAllFundCompany() {
        logger.info("====================\nall fund company={}", fundCompanyDao.listAllFundCompany(page));
    }

//    @Test
    public void testListFundCompanyBySearchField() {
        logger.info("====================\nfundCompany={}", fundCompanyDao.listFundCompanyBySearchField("12345", page));
        logger.info("====================\nfundCompany={}", fundCompanyDao.listFundCompanyBySearchField("test", page));
    }

//    @Test
    public void testCountFundCompany() {
        logger.info("====================\nfundCompanycnt={}", fundCompanyDao.countFundCompany());
    }

//    @Test
//    public void testInsertFundCompany() {
//        List<FundCompany> fundCompanies = new ArrayList<FundCompany>();
//        fundCompanies.add(new FundCompany("123456", "test_1", "123456test_1"));
//        fundCompanies.add(new FundCompany("123457", "test_2", "123457test_2"));
//        fundCompanyDao.insertFundCompany(fundCompanies);
//    }

//    @Test
//    public void testInsertAndGetId() {
//        FundCompany fundCompany = new FundCompany("123458", "test_3", "123458test_3");
//        fundCompanyDao.insertAndGetId(fundCompany);
//        logger.info("===============================id={}", fundCompany.getId());
//    }

    @Test
//    public void testUpdateFundCompany() {
//        FundCompany fundCompany = new FundCompany(13, "000000", "test_3", "000000test_3");
//        fundCompanyDao.updateFundCompany(fundCompany);
//    }

//    @Test
    public void testDeleteFundCompanyById() {
        fundCompanyDao.deleteFundCompanyById(10);
    }

//    @Test
    public void testDeleteFundCompanyBatchById() {
        fundCompanyDao.deleteFundCompanyBatchById(Arrays.asList(1,2,3));
    }

}
