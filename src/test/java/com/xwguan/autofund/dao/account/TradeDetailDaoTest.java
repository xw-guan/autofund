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

import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.service.template.account.TradeDetailTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class TradeDetailDaoTest {

    @Autowired
    private TradeDetailDao dao;

    @Autowired
    private TradeDetailTemplate template;

//     @Test
    public void testGetByAccountIdAndDate() {
        TradeDetail ah = dao.getByAccountIdAndDate(1L, LocalDate.of(2018, 3, 9));
        assertNotNull(ah);
    }

//     @Test
    public void testGetLatestByAccountId() {
        TradeDetail ah = dao.getLatestByAccountId(1L);
        assertNotNull(ah);
    }

//     @Test
    public void testListByAccountId() {
        List<TradeDetail> listByAccountId = dao.listByAccountId(1L, null);
        List<TradeDetail> listByAccountId2 = dao.listByAccountId(2L, null);
        assertTrue(listByAccountId.size() == 2);
        assertTrue(listByAccountId2.size() == 1);
    }

//     @Test
    public void testDeleteByAccountId() {
        fail("Not yet implemented"); // TODO
    }

//     @Test // passed
    public void testGetById() {
         TradeDetail ah = dao.getById(1);
         System.out.println(ah);
         assertNotNull(ah);
    }

//     @Test // passed
    public void testCUD() {
        TradeDetail tradeDetail = template.defaultTradeDetail(LocalDate.of(2018, 3, 10));
        tradeDetail.setAccountId(1L);
        int insertAndSetId = dao.insertAndSetId(tradeDetail);
        assertTrue(insertAndSetId == 1 && tradeDetail.getId() > 0);

        tradeDetail.setTradeShare(10.0);
        int update = dao.update(tradeDetail);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(tradeDetail.getId());
        assertTrue(deleteById == 1);
    }

     @Test // passed
    public void testCUDBatch() {
        int size = 10;
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        List<TradeDetail> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(template.defaultTradeDetail(LocalDate.of(year, month, dayOfMonth++)));
        }

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        list.parallelStream().forEach(e -> e.setTradeShare(10.0));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == size);
        
        list.parallelStream().forEach(e -> e.setTradeShare(11.0));
        int updateOrInsertBatch = dao.updateOrInsertBatch(list);
        assertTrue(updateOrInsertBatch == size * 2);

        list.parallelStream().forEach(e -> e.setTradeShare(12.0));
        TradeDetail e = template.defaultTradeDetail(LocalDate.of(year, month, dayOfMonth++));
        e.setId(10L);
        e.setTradeShare(998.0);
        list.add(e);
        int updateOrInsertBatch2 = dao.updateOrInsertBatch(list);
        assertTrue(updateOrInsertBatch2 == size * 2 +1);
        
        List<Long> ids = list.stream().map(TradeDetail::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size + 1);

    }

}
