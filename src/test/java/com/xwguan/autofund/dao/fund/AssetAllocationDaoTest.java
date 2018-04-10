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

import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.FundPosition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class AssetAllocationDaoTest {

    @Autowired
    private AssetAllocationDao assetAllocationDao;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Test
    public void testGetAssetAllocationById() {
        logger.info("====================\nassetAllocation={}", assetAllocationDao.getAssetAllocationById(1));
    }

     @Test
    public void testListAssetAllocationByFundId() {
        logger.info("====================\nassetAllocation={}", assetAllocationDao.getAssetAllocationByFundId(1));
    }

//    @Test
    public void testListAllAssetAllocation() {
//        logger.info("====================\nall assetAllocation={}", assetAllocationDao.listAllAssetAllocation());
         List<AssetAllocation> assetAllocationList = assetAllocationDao.listAllAssetAllocation();
         for (AssetAllocation aa : assetAllocationList) {
         System.out.println(aa.getId());
         System.out.println(aa.getFundId());
         for (FundPosition fp : aa.getStockList()) {
         System.out.println("stock: " + fp);
         }
         for (FundPosition fp : aa.getDebentureList()) {
         System.out.println("debenture: " + fp);
         }
         }
    }

    // @Test
    public void testInsertAssetAllocation() {
        List<AssetAllocation> assetAllocationList = new ArrayList<AssetAllocation>();
        assetAllocationList.add(new AssetAllocation(1, LocalDate.now(), 70.0, 10.0, 20.0, 0.0, 100.0));
        assetAllocationList.add(new AssetAllocation(2, LocalDate.now(), 100.0, 0.0, 0.0, 0.0, 100.0));
        assetAllocationDao.insertAssetAllocation(assetAllocationList);
    }

    // @Test
    public void testUpdateAssetAllocation() {
        AssetAllocation assetAllocation = new AssetAllocation(2, 2, LocalDate.now(), 100.0, 0.0, 0.0, 0.0, 200.0);
        assetAllocationDao.updateAssetAllocation(assetAllocation);
    }

    // @Test
    public void testDeleteAssetAllocationById() {
        assetAllocationDao.deleteAssetAllocationById(1);
    }

    // @Test
    public void testDeleteAssetAllocationByFundId() {
        assetAllocationDao.deleteAssetAllocationByFundId(2);
    }

    // @Test
    public void testDeleteAssetAllocationBatchById() {
        assetAllocationDao.deleteAssetAllocationBatchByFundId(Arrays.asList(1, 2));
    }

}
