package com.xwguan.autofund.dao.plan.tactic;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.service.template.plan.TacticTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class MaTacticDaoTest {

    @Autowired
    private MATacticDao dao;

    @Autowired
    private TacticTemplate template;

     @Test // passed
    public void testListByPlanId() {
         MATactic e = dao.getByPlanId(666L);
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
         MATactic e = dao.getById(666);
         System.out.println(e);
         assertNotNull(e);
         assertTrue(e.getRuleList().size() == 16);
    }

//     @Test // passed
    public void testCUD() {
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        MATactic maTactic = template.maTactic(LocalDate.of(year, month, dayOfMonth));
        maTactic.setPlanId(112L);
        int insertAndSetId = dao.insertAndSetId(maTactic);
        assertTrue(insertAndSetId == 1 && maTactic.getId() > 0);

        maTactic.setPlanId(100L);
        int update = dao.update(maTactic);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(maTactic.getId());
        assertTrue(deleteById == 1);
    }

}
