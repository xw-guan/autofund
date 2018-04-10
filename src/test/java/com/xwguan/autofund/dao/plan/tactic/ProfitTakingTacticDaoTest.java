package com.xwguan.autofund.dao.plan.tactic;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.service.template.plan.TacticTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class ProfitTakingTacticDaoTest {

    @Autowired
    private ProfitTakingTacticDao dao;

    @Autowired
    private TacticTemplate template;

     @Test // passed
    public void testListByPlanId() {
         ProfitTakingTactic e = dao.getByPlanId(666L);
         System.out.println(e);
         assertNotNull(e);
         assertTrue(e.getRuleList().size() == 16);
    }

//     @Test
    public void testDeleteByPlanId() {
        fail("Not yet implemented"); // TODO
    }

//     @Test // passed
    public void testGetById() {
         ProfitTakingTactic e = dao.getById(666);
         System.out.println(e);
         assertNotNull(e);
         assertTrue(e.getRuleList().size() == 16);
    }

//     @Test // passed
    public void testCUD() {
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        ProfitTakingTactic profitTakingTactic = template.planProfitTakingTactic(LocalDate.of(year, month, dayOfMonth));
        profitTakingTactic.setPlanId(1L);
        int insertAndSetId = dao.insertAndSetId(profitTakingTactic);
        assertTrue(insertAndSetId == 1 && profitTakingTactic.getId() > 0);

        profitTakingTactic.setPlanId(100L);
        int update = dao.update(profitTakingTactic);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(profitTakingTactic.getId());
        assertTrue(deleteById == 1);
    }

}
