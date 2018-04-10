package com.xwguan.autofund.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.service.api.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class AccountServiceTest {
    
    @Autowired
    private AccountService accountService;

//    @Test
    public void testGetAccountLongHistoryScopeEnumPage() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testGetAccountLongAccountOwnerTypeEnumHistoryScopeEnumPage() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testGetAccountLongLocalDateLocalDatePage() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertAccountAccount() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertAccountListOfAccount() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testDeleteAccountLong() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testDeleteAccountLongAccountOwnerTypeEnum() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testDeleteAccountListOfLong() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertAssetHistoryAssetHistory() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertAssetHistoryListOfAssetHistory() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertTradeDetailTradeDetail() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testInsertTradeDetailListOfTradeDetail() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testUpdateAssetHistoryAssetHistory() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testUpdateAssetHistoryListOfAssetHistory() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testUpdateTradeDetailTradeDetail() {
        fail("Not yet implemented"); // TODO
    }

//    @Test
    public void testUpdateTradeDetailListOfTradeDetail() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testHasAsset() {
        boolean hasAsset = accountService.hasAsset(9L, AccountOwnerTypeEnum.PLAN);
        System.out.println(hasAsset);
    }

//    @Test
    public void testNotHasAsset() {
        fail("Not yet implemented"); // TODO
    }

}
