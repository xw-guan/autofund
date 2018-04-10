package com.xwguan.autofund.dao.account;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.service.template.account.AssetHistoryTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class AssetHistoryDaoTest {

    @Autowired
    private AssetHistoryDao dao;

    @Autowired
    private AssetHistoryTemplate template;

    // @Test
    public void testGetByAccountIdAndDate() {
        AssetHistory ah = dao.getByAccountIdAndDate(1L, LocalDate.of(2018, 3, 9));
        assertNotNull(ah);
    }

    // @Test
    public void testGetLatestByAccountId() {
        AssetHistory ah = dao.getLatestByAccountId(1L);
        assertNotNull(ah);
    }

    // @Test
    public void testListByAccountId() {
        List<AssetHistory> listByAccountId = dao.listByAccountId(1L, null);
        List<AssetHistory> listByAccountId2 = dao.listByAccountId(2L, null);
        assertTrue(listByAccountId.size() == 3);
        assertTrue(listByAccountId2.size() == 1);
    }

    // @Test
    public void testDeleteByAccountId() {
        fail("Not yet implemented"); // TODO
    }

    // @Test // passed
    public void testGetById() {
        AssetHistory ah = dao.getById(1);
        System.out.println(ah);
        assertNotNull(ah);
    }

    // @Test // passed
    public void testCUD() {
        AssetHistory assetHistory = template.defaultAssetHistory(LocalDate.of(2018, 3, 10));
        assetHistory.setAccountId(1L);

        int insertAndSetId = dao.insertAndSetId(assetHistory);
        assertTrue(insertAndSetId == 1 && assetHistory.getId() > 0);

        assetHistory.setPosPrice(10.0);
        int update = dao.update(assetHistory);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(assetHistory.getId());
        assertTrue(deleteById == 1);
    }

    @Test // passed
    public void testCUDBatch() {
        int size = 10;
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        List<AssetHistory> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(template.defaultAssetHistory(LocalDate.of(year, month, dayOfMonth++)));
        }

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(ah -> assertTrue(ah.getId() > 0));

        list.parallelStream().forEach(ah -> ah.setPosPrice(10.0));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == size);

        list.parallelStream().forEach(e -> e.setPosPrice(11.0));
        int updateOrInsertBatch = dao.updateOrInsertBatch(list);
        assertTrue(updateOrInsertBatch == size * 2);

        list.parallelStream().forEach(ah -> ah.setPosPrice(12.0));
        AssetHistory e = template.defaultAssetHistory(LocalDate.of(year, month, dayOfMonth++));
        e.setId(10L);
        e.setPosPrice(998.0);
        list.add(e);
        int updateOrInsertBatch2 = dao.updateOrInsertBatch(list);
        assertTrue(updateOrInsertBatch2 == size * 2 + 1);

        List<Long> ids = list.stream().map(AssetHistory::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size + 1);

    }

}
