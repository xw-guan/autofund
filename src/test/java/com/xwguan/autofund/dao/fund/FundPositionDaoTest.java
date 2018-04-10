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
import com.xwguan.autofund.entity.fund.FundPosition;
import com.xwguan.autofund.enums.SecurityTypeEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class FundPositionDaoTest {

    @Autowired
    private FundPositionDao fundPositionDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Page page = new Page(1, 2);

     @Test
    public void testGetFundPositionById() {
        logger.info("====================\nfundPosition={}", fundPositionDao.getFundPositionById(1));
    }

//     @Test
    public void testListAllFundPosition() {
        logger.info("====================\nall fund company={}", fundPositionDao.listAllFundPosition(page));
    }

//     @Test
    public void testListFundPositionByAssetAllocationId() {
        logger.info("====================\nfundPosition={}", fundPositionDao.listFundPositionByAssetAllocationId(1, page));
    }

//     @Test
    public void testListFundPositionBySecurityType() {
        logger.info("====================\nfundPosition={}", fundPositionDao.listFundPositionBySecurityType(1, SecurityTypeEnum.STOCK, page));
    }

//     @Test
    public void testInsertFundPosition() {
        List<FundPosition> fundmanagerList = new ArrayList<FundPosition>();
        fundmanagerList.add(new FundPosition(1, SecurityTypeEnum.STOCK, "123456", 10.0));
        fundmanagerList.add(new FundPosition(1, SecurityTypeEnum.STOCK, "123457", 10.0));
        fundmanagerList.add(new FundPosition(2, SecurityTypeEnum.STOCK, "123456", 10.0));
        fundmanagerList.add(new FundPosition(3, SecurityTypeEnum.STOCK, "123456", 10.0));
        fundPositionDao.insertFundPosition(fundmanagerList);
    }

//     @Test
    public void testUpdateFundPosition() {
        FundPosition fundPosition = new FundPosition(13l, 1, SecurityTypeEnum.STOCK, "000000", 10.0);
        fundPositionDao.updateFundPosition(fundPosition);
    }

//     @Test
    public void testDeleteFundPositionById() {
        fundPositionDao.deleteFundPositionById(8);
    }

//     @Test
    public void testDeleteFundPositionByAssetAllocationId() {
        fundPositionDao.deleteFundPositionByAssetAllocationId(1);
    }
    
//     @Test
    public void testDeleteFundPositionBatchById() {
        fundPositionDao.deleteFundPositionBatchByAssetAllocationId(Arrays.asList(1, 2, 3));
    }

}
