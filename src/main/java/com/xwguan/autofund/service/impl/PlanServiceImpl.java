package com.xwguan.autofund.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.dao.plan.PlanDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.enums.PlanTemplateEnum;
import com.xwguan.autofund.enums.TacticTemplateEnum;
import com.xwguan.autofund.exception.FailGettingRealTimeDataException;
import com.xwguan.autofund.exception.NotTradeDayException;
import com.xwguan.autofund.exception.account.DeleteAccountException;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidParamException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.exception.plan.PlanServiceException;
import com.xwguan.autofund.exception.plan.UnknownTemplateCodeException;
import com.xwguan.autofund.service.api.AccountService;
import com.xwguan.autofund.service.api.PlanService;
import com.xwguan.autofund.service.api.PositionService;
import com.xwguan.autofund.service.api.TacticService;
import com.xwguan.autofund.service.handler.plan.PlanAssembler;
import com.xwguan.autofund.service.template.plan.PlanTemplate;
import com.xwguan.autofund.service.util.Filters;
import com.xwguan.autofund.service.util.Predicates;

/**
 * 计划服务实现
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-07
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanDao planDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private TacticService tacticSerVice;

    @Autowired
    private PlanTemplate planTemplate;

    @Autowired
    private PlanAssembler assembler;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PlanTradeScheme getTradeScheme(Plan plan, LocalDate tradeDate)
        throws FailGettingRealTimeDataException, NotTradeDayException {
        try {
            return plan.handler().generateTradeScheme(tradeDate)
                .orElseThrow(() -> new NotTradeDayException("The date(" + tradeDate + ") is not a trade day"));
        } catch (IOException e) {
            throw new FailGettingRealTimeDataException("Fail getting real-time stock data from netease");
        }
    }

    @Override
    public PlanTradeScheme getTradeScheme(Long planId, LocalDate tradeDate)
        throws FailGettingRealTimeDataException, NotTradeDayException {
        Plan plan = getFullPlanByPlanId(planId, HistoryScopeEnum.NONE);
        return getTradeScheme(plan, tradeDate);
    }

    @Override
    public List<Plan> listPlanByUserId(Integer userId, Page page) {
        if (userId == null || userId <= 0) {
            logger.debug("Invalid userid");
            return new ArrayList<>();
        }
        return planDao.listByUserId(userId, page);
    }

    @Override
    public Plan getPlanByPlanId(Long planId) {
        if (planId == null || planId <= 0) {
            logger.debug("Invalid userid");
            return null;
        }
        return planDao.getById(planId);
    }

    @Override
    public Plan getFullPlanByPlanId(Long planId, HistoryScopeEnum historyScope) {
        Plan plan = getPlanByPlanId(planId);
        if (plan == null) {
            return null;
        }
        assembler.assembleAllTactics(plan);
        assembler.assemblePositions(plan, historyScope);
        assembler.assembleAccount(plan, historyScope);
        return plan;
    }

    @Override
    public Plan cleanCopyAndResetPlan(Plan plan, LocalDate initDate) throws InvalidParamException {
        if (plan == null) {
            throw new InvalidParamException("the plan to be copy requires not null");
        }
        if (initDate == null) {
            initDate = LocalDate.now();
        }
        return plan.handler().cleanCopyAndReset(initDate);
    }

    @Override
    public Plan cleanCopyAndResetPlan(Long planId, LocalDate initDate) throws InvalidParamException {
        if (planId == null || planId <= 0) {
            throw new InvalidParamException("the planId(" + planId + ") is invalid");
        }
        Plan plan = getFullPlanByPlanId(planId, HistoryScopeEnum.NONE);
        return cleanCopyAndResetPlan(plan, initDate);
    }

    @Override
    @Transactional
    public int insertPlan(Plan plan) {
        if (plan == null) {
            logger.debug("Empty plan");
            return 0;
        }
        int cntInsPlan = planDao.insertAndSetId(plan);
        Long planId = plan.getId();
        // insert account if present
        Account account = plan.getAccount();
        if (account != null) {
            account.setOwnerId(planId);
            account.setOwnerType(AccountOwnerTypeEnum.PLAN);
            accountService.insertAccount(plan.getAccount());
        }
        // insert positions if present
        List<Position> positionList = plan.getPositionList();
        if (CollectionUtils.isNotEmpty(positionList)) {
            positionList.stream().forEach(pst -> pst.setPlanId(planId));
            positionService.insertPosition(positionList);
        }
        // insert tactics if present
        List<Tactic> tacticList = getAllTacticsFromPlan(plan);
        if (CollectionUtils.isNotEmpty(tacticList)) {
            tacticList.stream().forEach(t -> t.setPlanId(planId));
            tacticSerVice.insertTactic(tacticList);
        }
        logger.debug("cntInsPlan = " + cntInsPlan);
        return cntInsPlan;
    }

    @Override
    @Transactional
    public int updatePlan(Plan plan) {
        if (plan == null) {
            logger.debug("Empty plan");
            return 0;
        }
        if (Predicates.notGreaterThanZero(plan.getId())) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntUpdPlan = planDao.update(plan);
        logger.debug("cntUpdPlan = " + cntUpdPlan);
        return cntUpdPlan;
    }

    @Override
    @Transactional
    public int updateTactics(Plan plan) {
        int cntUpdTacticSum = getAllTacticsFromPlan(plan).stream().mapToInt(t -> tacticSerVice.updateTactic(t)).sum();
        logger.debug("cntUpdTacticSum = " + cntUpdTacticSum);
        return cntUpdTacticSum;
    }

    @Override
    @Transactional
    public int deletePlan(Long planId) throws DeleteAccountException {
        if (planId == null || planId <= 0) {
            return 0;
        }
        if (accountService.hasAsset(planId, AccountOwnerTypeEnum.PLAN)) {
            throw new DeleteAccountException("Cannot delete beacause the account still has asset");
        }
        accountService.deleteAccount(planId, AccountOwnerTypeEnum.PLAN);
        positionService.deletePositionOfPlan(planId);
        tacticSerVice.deleteTacticOfPlan(planId);
        int cntDelPlan = planDao.deleteById(planId);
        logger.debug("cntDelPlan = " + cntDelPlan);
        return cntDelPlan;
    }

    @Override
    @Transactional
    public int deletePlanOfUser(Integer userId) throws DeleteAccountException {
        if (userId == null || userId <= 0) {
            return 0;
        }
        List<Long> filteredIds = Filters.filterValidId(planDao.listIdByUserId(userId));
        int cntDelPlanSum = 0;
        for (Long planId : filteredIds) {
            cntDelPlanSum += deletePlan(planId);
        }
        logger.debug("cntDelPlanSum = " + cntDelPlanSum);
        return cntDelPlanSum;
    }

    @Override
    public Plan getTemplate(String templateCode) throws UnknownTemplateCodeException {
        return getTemplate(templateCode, LocalDate.now());
    }

    @Override
    public Plan getTemplate(String templateCode, LocalDate initDate) throws UnknownTemplateCodeException {
        PlanTemplateEnum template = PlanTemplateEnum.of(templateCode);
        switch (template) {
        case MA_PLAN:
            return planTemplate.maPlan(initDate);
        case GAIN_LOSS_PLAN:
            return planTemplate.gainLossPlan(initDate);
        case PERIOD_BUY_PLAN:
            return planTemplate.periodBuyPlan(initDate);
        case MA_WITH_PROFIT_TAKING:
            return planTemplate.maPlanWithProfitTaking(initDate);
        default:
            throw new UnknownTemplateCodeException("Unknown template code");
        }
    }

    @Override
    public Tactic addTactic(Plan plan, String templateCode) throws UnknownTemplateCodeException {
        return addTactic(plan, templateCode, LocalDate.now());
    }

    @Override
    public Tactic addTactic(Plan plan, String templateCode, LocalDate initDate) throws UnknownTemplateCodeException {
        TacticTemplateEnum template = TacticTemplateEnum.of(templateCode);
        switch (template) {
        case GAIN_LOSS_TACTIC:
            return planTemplate.addGainLossTactic(plan, initDate);
        case MA_TACTIC_250:
            return planTemplate.addMATactic(plan, initDate);
        case INDEX_CHANGE_TACTIC_22:
            return planTemplate.addIndexChangeTactic(plan, initDate);
        case NAV_CHANGE_TACTIC_22:
            return planTemplate.addNavChangeTactic(plan, initDate);
        case PERIOD_BUY_TACTIC:
            return planTemplate.addPeriodBuyTactic(plan, initDate);
        case PLAN_PROFIT_TAKING_TACTIC:
            return planTemplate.addPlanProfitTakingTactic(plan, initDate);
        case POSITION_PROFIT_TAKING_TACTIC:
            return planTemplate.addPositionProfitTakingTactic(plan, initDate);
        default:
            throw new UnknownTemplateCodeException("Unknown template code");
        }
    }

    @Override
    public Position addPosition(Plan plan, String fundCode, String refIndexSymbol) throws PlanServiceException {
        try {
            return planTemplate.addPosition(plan, fundCode, refIndexSymbol);
        } catch (InvalidFundCodeException | InvalidTickerSymbolException e) {
            throw new PlanServiceException(e.getMessage(), e);
        }
    }

//    private void restartPlan(Plan plan) {
//        // TODO
//    }

    /**
     * 从Plan对象中获取全部策略列表
     */
    private List<Tactic> getAllTacticsFromPlan(Plan plan) {
        return plan.handler().setConnDbForData(false).getAllTactics();
    }
}
