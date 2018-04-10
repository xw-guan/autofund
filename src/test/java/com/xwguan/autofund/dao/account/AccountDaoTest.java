package com.xwguan.autofund.dao.account;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.service.template.account.AccountTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountTemplate accountTemplate;

     @Test // passed
    public void testGetById() {
        Account account;
        for (HistoryScopeEnum scope : HistoryScopeEnum.values()) {
            account = accountDao.getById(1L, scope, null);
            System.out.println(account);
            int ahSize = account.getAssetHistoryList().size();
            int tdSize = account.getTradeDetailList().size();
            System.out.println(ahSize + ", " + tdSize);
            assertTrue(scope == HistoryScopeEnum.FULL
                ? ahSize >= scope.ordinal()
                : ahSize == scope.ordinal());
            assertTrue(scope == HistoryScopeEnum.FULL
                ? tdSize >= scope.ordinal()
                : tdSize == scope.ordinal());
        }
        account = accountDao.getById(1L, null, null);
        assertTrue(account.getAssetHistoryList().size() == 0);
//        account = accountDao.getById(1L); // TODO 报错
//        assertTrue(account.getAssetHistoryList().size() == 0);
        
    }

     @Test // PASSED
    public void testGetAccountByOwnerIdAndType() {
        Account account;
        for (HistoryScopeEnum scope : HistoryScopeEnum.values()) {
            account = accountDao.getByOwnerIdAndType(1L, AccountOwnerTypeEnum.USER, scope, null);
            System.out.println(account);
            int ahSize = account.getAssetHistoryList().size();
            int tdSize = account.getTradeDetailList().size();
            System.out.println(ahSize + ", " + tdSize);
            assertTrue(scope == HistoryScopeEnum.FULL
                ? ahSize >= scope.ordinal()
                : ahSize == scope.ordinal());
            assertTrue(scope == HistoryScopeEnum.FULL
                ? tdSize >= scope.ordinal()
                : tdSize == scope.ordinal());
        }
    }

//     @Test // passed
    public void testGetIdByOwnerIdAndType() {
        Long idByOwnerIdAndType = accountDao.getIdByOwnerIdAndType(1L, AccountOwnerTypeEnum.USER);
        assertTrue(idByOwnerIdAndType == 1);
    }

    // @Test // PASSED
    public void testInsertAndSetId() {
        Account account = accountTemplate.of(AccountOwnerTypeEnum.USER, 1L);
        int insertAndSetId = accountDao.insertAndSetId(account);
        System.out.println(insertAndSetId + ", " + account);
        assertTrue((insertAndSetId == 0 && account.getId() == -1) || (insertAndSetId == 1 && account.getId() > 0));
    }


    // @Test
    public void testDeleteById() {
        int deleteById = accountDao.deleteById(1L);
        System.out.println(deleteById);
    }

    // @Test
    public void testInsertBatch() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testUpdateBatch() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testDeleteBatch() {
        fail("Not yet implemented"); // TODO
    }

}
