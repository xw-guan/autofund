package com.xwguan.autofund.dao.plan;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.service.template.plan.PositionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class PositionDaoTest {

    @Autowired
    private PositionDao dao;

    @Autowired
    private PositionTemplate template;

//     @Test
    public void testGetById() {
        Position position = dao.getById(1L, true);
        System.out.println(position);
        assertTrue(position.getAccount().getAssetHistoryList().size() == 1);
        position = dao.getById(1L, false);
        assertTrue(position.getAccount().getAssetHistoryList().size() == 0);
//        position = dao.getById(1L); // TODO 报错
//        assertTrue(position.getAccount().getAssetHistoryList().size() == 0);
    }

//     @Test // passed
     public void testListByPlanId() {
         List<Position> list = dao.listByPlanId(1L, null, null);
         System.out.println(list);
         assertTrue(list.size() == 3);
         
         list = dao.listByPlanId(2L, null, null);
         System.out.println(list);
         assertTrue(list.size() == 2);
     }

//      @Test
     public void testDeleteByAccountId() {
         fail("Not yet implemented"); // TODO
     }
     
//     @Test
     public void testUpdateRefIndexId() {
         dao.updateRefIndexId(1L, null);
     }

//      @Test // passed
     public void testCUD() {
         Position position = template.csi300();
         position.setPlanId(1L);
         int insertAndSetId = dao.insertAndSetId(position);
         assertTrue(insertAndSetId == 1 && position.getId() > 0);

         position.setFundId(1000);
         int update = dao.update(position);
         assertTrue(update == 1);

         int deleteById = dao.deleteById(position.getId());
         assertTrue(deleteById == 1);
     }

      @Test // passed
     public void testCUDBatch() {
         List<Position> list = template.csi300And500().stream().peek(pst -> pst.setPlanId(1L)).collect(Collectors.toList());
         list.addAll(template.csi300And500().stream().peek(pst -> pst.setPlanId(2L)).collect(Collectors.toList()));
//          List<Position> list = new ArrayList<Position>();

         int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
         assertTrue(insertAndSetIdBatch == 4);
         list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

         list.parallelStream().forEach(e -> e.setFundId(1000));
         int updateBatch = dao.updateBatch(list);
         assertTrue(updateBatch == 4);

         List<Long> ids = list.stream().map(Position::getId).collect(Collectors.toList());
         int deleteBatch = dao.deleteByIdListBatch(ids);
         assertTrue(deleteBatch == 4);

     }

}
