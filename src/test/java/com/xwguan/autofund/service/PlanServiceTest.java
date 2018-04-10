package com.xwguan.autofund.service;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.exception.FailGettingRealTimeDataException;
import com.xwguan.autofund.exception.NotTradeDayException;
import com.xwguan.autofund.exception.account.DeleteAccountException;
import com.xwguan.autofund.exception.plan.UnknownTemplateCodeException;
import com.xwguan.autofund.service.api.PlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    // @Test // pass
    public void testGetTradeScheme()
        throws UnknownTemplateCodeException, FailGettingRealTimeDataException, NotTradeDayException {
        LocalDate initDate = LocalDate.of(2018, 1, 31);
        Plan plan = planService.getTemplate("MA", initDate);
        plan.getPositionTacticList().stream().map(PositionTactic::getPeriodCondition)
            .forEach(pc -> pc.handler().resetPeriodCondition(AutoInvestPeriodEnum.DAILY, 1, initDate));
        System.out.println(plan);
        LocalDate date1 = LocalDate.of(2018, 1, 1);
        try {
            System.out.println(planService.getTradeScheme(plan, date1));
        } catch (NotTradeDayException e) {
            System.out.println(true);
        }
        LocalDate date2 = LocalDate.of(2018, 2, 1);
        PlanTradeScheme tradeScheme = planService.getTradeScheme(plan, date2);
        System.out.println(tradeScheme);
        assertTrue(tradeScheme.getTotalBuy() > 0);
    }

    // @Test
    public void testListPlanByUserId() {
        fail("Not yet implemented"); // TODO
    }

     @Test
    public void testGetPlanByPlanId() {
        Plan plan = planService.getPlanByPlanId(9L);
        System.out.println(plan);
    }

//    @Test
    public void testGetFullPlanByPlanId() {
        for (HistoryScopeEnum historyScope : HistoryScopeEnum.values()) {
            Plan plan = planService.getFullPlanByPlanId(9L, historyScope);
            System.out.println(historyScope);
            System.out.println(plan);
            assertNotNull(plan);
        }
    }

    // @Test
    public void testGetPlanInfoById() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testCleanCopyAndResetPlan() {
        fail("Not yet implemented"); // TODO
    }

//     @Test
    public void testInsertPlan() throws UnknownTemplateCodeException {
        LocalDate initDate = LocalDate.of(2018, 1, 31);
        Plan plan = planService.getTemplate("MA", initDate);
        plan.getPositionTacticList().stream().map(PositionTactic::getPeriodCondition)
            .forEach(pc -> pc.handler().resetPeriodCondition(initDate));
        int insertPlan = planService.insertPlan(plan);
        assertTrue(insertPlan == 1);
    }

    // @Test
    public void testUpdatePlan() {
        fail("Not yet implemented"); // TODO
    }

    // @Test
    public void testUpdateTactics() {
        fail("Not yet implemented"); // TODO
    }

//     @Test
    public void testDeletePlan() throws DeleteAccountException {
       int deletePlan = planService.deletePlan(11L);
       System.out.println(deletePlan);
    }

//     @Test
    public void testDeletePlanOfUser() throws DeleteAccountException {
        int deletePlan = planService.deletePlanOfUser(-1);
        System.out.println(deletePlan);
    }


}
