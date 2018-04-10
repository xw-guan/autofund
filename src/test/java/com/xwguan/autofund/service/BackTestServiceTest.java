package com.xwguan.autofund.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.api.BackTestService;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;
import com.xwguan.autofund.service.template.plan.PlanTemplate;
import com.xwguan.autofund.service.template.plan.PositionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class BackTestServiceTest {

    @Autowired
    private BackTestService backTestService;
    
    @Autowired
    private PlanTemplate planTemplate;

    @Autowired
    private PositionTemplate positionTemplate;

    @Autowired
    private PeriodConditionHandler periodConditionHandler;

    @Test
    public void testBackTest() throws InvalidFundCodeException, InvalidTickerSymbolException {
        LocalDate startDate = LocalDate.of(2016, 12, 15);
        LocalDate endDate = LocalDate.of(2018, 2, 6);
        Plan plan = planTemplate.maPlan(startDate);
        Position position = positionTemplate.csi300();
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        plan.setPositionList(positionList);
        periodConditionHandler.handle(plan.getPositionTacticList().get(0).getPeriodCondition())
            .resetPeriodCondition(AutoInvestPeriodEnum.WEEKLY, 1, startDate);
        System.out.println(plan);
        try {
            PlanBackTestResult backTestResult = backTestService.backTest(plan, startDate, endDate);
            System.out.println(backTestResult.result());
//            System.out.println(backTestResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testBackTestId() throws InvalidFundCodeException, InvalidTickerSymbolException {
        LocalDate startDate = LocalDate.of(2016, 12, 15);
        LocalDate endDate = LocalDate.of(2018, 2, 6);
        Long planId = 12L;
        try {
            PlanBackTestResult backTestResult = backTestService.backTest(planId, startDate, endDate);
            System.out.println("====================2=====================");
            System.out.println(backTestResult.result());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
