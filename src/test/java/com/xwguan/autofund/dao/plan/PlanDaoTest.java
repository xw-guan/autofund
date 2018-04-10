package com.xwguan.autofund.dao.plan;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.enums.PlanExecutionModeEnum;
import com.xwguan.autofund.service.template.plan.PlanTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class PlanDaoTest {

    @Autowired
    private PlanDao dao;

    @Autowired
    private PlanTemplate template;

     @Test // passed
    public void testGetById() {
        Plan plan = dao.getById(1L);
        System.out.println(plan);
        assertNotNull(plan);
    }

//     @Test // passed
    public void testListByUserId() {
        List<Plan> list = dao.listByUserId(1, null);
        System.out.println(list);
        assertTrue(list.size() == 2);

        list = dao.listByUserId(2, null);
        System.out.println(list);
        assertTrue(list.size() == 1);
    }

    // @Test
    public void testDeleteByAccountId() {
        fail("Not yet implemented"); // TODO
    }

//    @Test // passed
    public void testCUD() {
        Plan position = template.defaultPlan();
        position.setUserId(1L);
        int insertAndSetId = dao.insertAndSetId(position);
        assertTrue(insertAndSetId == 1 && position.getId() > 0);

        position.setExecutionMode(PlanExecutionModeEnum.AUTO_TRADE);
        int update = dao.update(position);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(position.getId());
        assertTrue(deleteById == 1);
    }

//    @Test // passed
    public void testCUDBatch() {
        List<Plan> list = new ArrayList<>();
        Plan defaultPlan = template.defaultPlan();
        defaultPlan.setUserId(10L);
        Plan maPlan = template.maPlan();
        maPlan.setUserId(10L);
        list.add(defaultPlan);
        list.add(maPlan);

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == 2);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        list.parallelStream().forEach(e -> e.setExecutionMode(PlanExecutionModeEnum.MSG));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == 2);

        List<Long> ids = list.stream().map(Plan::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == 2);

    }

}
