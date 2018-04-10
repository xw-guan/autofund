package com.xwguan.autofund.service.mapper.plan;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.dto.plan.LatestPlanDto;
import com.xwguan.autofund.dto.plan.PlanDto;
import com.xwguan.autofund.dto.plan.PlanInfoDto;
import com.xwguan.autofund.dto.plan.tactic.FlatTacticsDto;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.service.mapper.account.AccountMapper;
import com.xwguan.autofund.service.mapper.tactic.TacticsMapper;

public abstract class PlanMapperDecorator implements PlanMapper {
    @Autowired
    @Qualifier("delegate")
    private PlanMapper delegate;

    @Autowired
    private TacticsMapper tacticsMapper;
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Override
    public PlanDto toPlanDto(Plan plan) {
        PlanDto planDto = delegate.toPlanDto(plan);
        planDto.setPlanInfo(toPlanInfoDto(plan));
        List<Tactic> allTactics = plan.handler().setConnDbForData(false).getAllTactics();
        planDto.setTactics(tacticsMapper.toFlatTacticsDto(allTactics));
        return planDto;
    }

    @Override
    public Plan toPlan(PlanDto planDto) {
        Plan plan = delegate.toPlan(planDto);
        PlanInfoDto planInfo = planDto.getPlanInfo();
        if (planInfo != null) {
            updatePlan(plan, planInfo);
        }
        FlatTacticsDto tactics = planDto.getTactics();
        if (tactics != null) {
            plan.setPlanTacticList(tacticsMapper.toPlanTactics(tactics));
            plan.setPositionTacticList(tacticsMapper.toPositionTactics(tactics));
        }
        return plan;
    }

    @Override
    public LatestPlanDto toLatestPlanDto(Plan plan) {
        LatestPlanDto latestPlanDto = delegate.toLatestPlanDto(plan);
        if (latestPlanDto == null) {
            return null;
        }
        LatestAccountDto latestAccountDto = accountMapper.toLatestAccountDto(plan.getAccount());
        latestPlanDto.setLatestAccount(latestAccountDto);
        latestPlanDto.setPlanInfo(toPlanInfoDto(plan));
        List<Tactic> allTactics = plan.handler().setConnDbForData(false).getAllTactics();
        latestPlanDto.setTactics(tacticsMapper.toFlatTacticsDto(allTactics));
        return latestPlanDto;
    }
    
    

}
