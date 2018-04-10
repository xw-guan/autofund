package com.xwguan.autofund.service.handler.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.account.AccountDao;
import com.xwguan.autofund.dao.plan.PlanHistoryDao;
import com.xwguan.autofund.dao.plan.PositionDao;
import com.xwguan.autofund.dao.plan.tactic.TacticDaos;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;

/**
 * Plan对象装配器, id大于0且对应属性为空时, 从数据库获取并装配到Plan中.
 * <p>直接用SQL获取完整的Plan对象, 会有过于复杂的JOIN语句, 实际上也不需要完整的Plan对象, 这在装配器中按需实现.
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-15
 */
@Component
public class PlanAssembler {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private PlanHistoryDao planHistoryDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private PositionAssembler positionAssembler;

    public void assembleAllTactics(Plan plan) {
        assemblePlanTactics(plan);
        assemblePositionTactics(plan);
    }

    public void assemblePlanTactics(Plan plan) {
        if (plan.getPlanTacticList() == null) {
            plan.setPlanTacticList(new ArrayList<>());
        }
        if (plan.getId() > 0 && CollectionUtils.isEmpty(plan.getPlanTacticList())) {
            TacticDaos.listPlanTacticDao().parallelStream()
                .map(dao -> dao.getByPlanId(plan.getId()))
                .filter(Objects::nonNull)
                .forEach(t -> plan.getPlanTacticList().add(t));
        }
    }

    public void assemblePositionTactics(Plan plan) {
        if (plan.getPositionTacticList() == null) {
            plan.setPositionTacticList(new ArrayList<>());
        }
        if (plan.getId() > 0 && CollectionUtils.isEmpty(plan.getPositionTacticList())) {
            TacticDaos.listPositionTacticDao().parallelStream()
                .map(dao -> dao.getByPlanId(plan.getId()))
                .filter(Objects::nonNull)
                .forEach(t -> plan.getPositionTacticList().add(t));
        }
    }

    public void assemblePositions(Plan plan) {
        assemblePositions(plan, HistoryScopeEnum.NONE);
    }

    public void assemblePositions(Plan plan, HistoryScopeEnum historyScope) {
        if (plan.getPositionList() == null) {
            plan.setPositionList(new ArrayList<>());
        }
        Long planId = plan.getId();
        List<Position> positionList = plan.getPositionList();
        if (planId > 0 && CollectionUtils.isEmpty(positionList)) {
            positionDao.listByPlanId(planId, false, null).stream()
                .peek(pst -> positionAssembler.assembleAccount(pst, historyScope))
                .forEach(pst -> positionList.add(pst));
        }
    }

    public void assembleAccount(Plan plan) {
        assembleAccount(plan, HistoryScopeEnum.NONE);
    }

    public void assembleAccount(Plan plan, HistoryScopeEnum historyScope) {
        if (plan.getId() > 0 && plan.getAccount() == null) {
            plan.setAccount(
                accountDao.getByOwnerIdAndType(
                    plan.getId(), AccountOwnerTypeEnum.PLAN, historyScope, null));
        }
    }

    public void assemblePlanHistories(Plan plan) {
        if (plan.getId() > 0 && CollectionUtils.isEmpty(plan.getPlanHistoryList())) {
            plan.getPlanHistoryList().addAll(
                planHistoryDao.listByPlanId(plan.getId(), null));
        }
    }
}
