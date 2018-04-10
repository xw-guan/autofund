package com.xwguan.autofund.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.dao.plan.rule.OperationDao;
import com.xwguan.autofund.dao.plan.rule.PeriodConditionDao;
import com.xwguan.autofund.dao.plan.rule.RangeConditionDao;
import com.xwguan.autofund.dao.plan.rule.RuleDao;
import com.xwguan.autofund.dao.plan.tactic.TacticDao;
import com.xwguan.autofund.dao.plan.tactic.TacticDaos;
import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTemplateEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.exception.plan.TacticTemplateException;
import com.xwguan.autofund.service.api.TacticService;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;
import com.xwguan.autofund.service.template.plan.TacticTemplate;
import com.xwguan.autofund.service.util.Filters;
import com.xwguan.autofund.service.util.Predicates;
import com.xwguan.autofund.util.IocUtils;

@Service
public class TacticServiceImpl implements TacticService {

    @Autowired
    private RuleDao ruleDao;

    @Autowired
    private PeriodConditionDao periodConditionDao;

    @Autowired
    private RangeConditionDao rangeConditionDao;

    @Autowired
    private OperationDao operationDao;

    @Autowired
    private TacticTemplate tacticTemplate;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Tactic getTactic(Tactic tactic) {
        if (tactic == null) {
            logger.debug("Empty tactic");
            return null;
        }
        Long tacticId = tactic.getId();
        TacticTypeEnum tacticType = TacticTypeEnum.of(tactic);
        return getTactic(tacticId, tacticType);
    }

    @Override
    public Tactic getTactic(Long tacticId, TacticTypeEnum tacticType) {
        if (tacticId == null || tacticId <= 0) {
            logger.debug("Invalid id");
            return null;
        }
        if (tacticType == null) {
            logger.debug("Invalid type");
            return null;
        }
        return TacticDaos.getDao(tacticType).getById(tacticId);
    }

    @Override
    public List<PlanTactic> listPlanTacticByPlanId(Long planId) {
        if (planId != null && planId <= 0) {
            logger.debug("Invalid planId");
            return new ArrayList<>();
        }
        return TacticDaos.listPlanTacticDao().stream()
            .map(dao -> dao.getByPlanId(planId))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public List<PositionTactic> listPositionTacticByPlanId(Long planId) {
        if (planId != null && planId <= 0) {
            logger.debug("Invalid planId");
            return new ArrayList<>();
        }
        return TacticDaos.listPositionTacticDao().stream()
            .map(dao -> dao.getByPlanId(planId))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public Tactic getTacticTemplate(String templateCode) throws TacticTemplateException {
        TacticTemplateEnum templateEnum = TacticTemplateEnum.of(templateCode);
        LocalDate initDate = LocalDate.now();
        Tactic tactic = tacticTemplate.getTemplate(templateEnum, initDate);
        if (tactic == null) {
            throw new TacticTemplateException("Unknown template code: " + templateCode);
        }
        return tactic;
    }

    @Override
    public List<Tactic> listAllTacticTemplate() {
        LocalDate initDate = LocalDate.now();
        return Stream.of(TacticTemplateEnum.values())
            .map(t -> tacticTemplate.getTemplate(t, initDate))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int insertTactic(Tactic tactic) {
        if (tactic == null) {
            logger.debug("Empty tactic");
            return 0;
        }
        int cntInsTactic = TacticDaos.getDao(tactic).insertAndSetId(tactic);
        Long tacticId = tactic.getId();
        PeriodCondition periodCondition = tactic.getPeriodCondition();
        if (periodCondition != null) {
            periodCondition.setTacticId(tacticId);
            periodCondition.handler().resetAndClearPeriodCondition(LocalDate.now());
            insertPeriodCondition(periodCondition);
        }
        List<Rule> ruleList = tactic.getRuleList();
        if (CollectionUtils.isNotEmpty(ruleList)) {
            ruleList.stream().forEach(r -> r.setTacticId(tacticId));
            insertRule(ruleList);
        }
        logger.debug("cntInsTactic = " + cntInsTactic);
        return cntInsTactic;
    }

    @Override
    @Transactional
    public int insertTactic(List<Tactic> tacticList) {
        if (CollectionUtils.isEmpty(tacticList)) {
            logger.debug("Empty tacticList");
            return 0;
        }
        int cntInsTacticSum = tacticList.parallelStream().mapToInt(t -> insertTactic(t)).sum();
        logger.debug("cntInsTacticSum = " + cntInsTacticSum);
        return cntInsTacticSum;
    }

    @Override
    @Transactional
    public int deleteTactic(Tactic tactic) {
        if (tactic == null) {
            logger.debug("Empty tactic");
            return 0;
        }
        Long tacticId = tactic.getId();
        TacticTypeEnum tacticType = TacticTypeEnum.of(tactic);
        return deleteTactic(tacticId, tacticType);
    }

    @Override
    public int deleteTactic(Long tacticId, TacticTypeEnum tacticType) {
        if (tacticId == null || tacticId <= 0) {
            logger.debug("Invalid id");
            return 0;
        }
        if (tacticType == null) {
            logger.debug("Invalid type");
            return 0;
        }
        deletePeriodCondition(tacticId, tacticType);
        deleteRule(tacticId, tacticType);
        int cntDelTactic = TacticDaos.getDao(tacticType).deleteById(tacticId);
        logger.debug("cntDelTactic = " + cntDelTactic);
        return cntDelTactic;
    }

    @Override
    @Transactional
    public int deleteTactic(List<Tactic> tacticList) {
        if (CollectionUtils.isEmpty(tacticList)) {
            logger.debug("Empty tacticList");
            return 0;
        }
        int cntDelTacticSum = tacticList.parallelStream().mapToInt(t -> deleteTactic(t)).sum();
        logger.debug("cntDelTacticSum = " + cntDelTacticSum);
        return cntDelTacticSum;
    }

    @Override
    @Transactional
    public int deleteTacticOfPlan(Long planId) {
        if (planId == null || planId <= 0) {
            logger.debug("Invalid planId");
            return 0;
        }
        for (TacticTypeEnum tacticType : TacticTypeEnum.values()) {
            Long tacticId = TacticDaos.getDao(tacticType).getIdByPlanId(planId);
            if (tacticId == null) {
                continue;
            }
            deletePeriodCondition(tacticId, tacticType);
            deleteRule(tacticId, tacticType);
        }
        int cntDelTactic = TacticDaos.listTacticDao().parallelStream()
            .mapToInt(dao -> dao.deleteByPlanId(planId)).sum();
        logger.debug("cntDelTactic = " + cntDelTactic);
        return cntDelTactic;
    }

    @Override
    @Transactional
    public int updateTactic(Tactic tactic) {
        if (tactic == null) {
            logger.debug("Empty tactic");
            return 0;
        }
        if (Predicates.notGreaterThanZero(tactic.getId())) {
            return 0;
        }
        TacticDao<? extends Tactic> dao = TacticDaos.getDao(tactic);
        int cntUpdTactic = dao.update(tactic);
        logger.debug("cntUpdTactic = " + cntUpdTactic);
        PeriodCondition periodCondition = tactic.getPeriodCondition();
        if (periodCondition != null) {
            periodCondition.handler().resetPeriodCondition(LocalDate.now());
            updatePeriodCondition(periodCondition);
        }
        List<Rule> ruleList = tactic.getRuleList();
        if (CollectionUtils.isNotEmpty(ruleList)) {
            changeRule(ruleList);
        }
        return cntUpdTactic;
    }

    @Override
    @Transactional
    public int changeTactic(List<Tactic> tacticList) {
        if (CollectionUtils.isEmpty(tacticList)) {
            logger.debug("Empty tacticList");
            return 0;
        }
        deleteTactic(tacticList);
        return insertTactic(tacticList);
    }

    @Override
    public int resetAndUpdatePeriodCondition(PeriodCondition periodCondition) {
        return IocUtils.getBean(PeriodConditionHandler.class).handle(periodCondition)
            .resetPeriodCondition(LocalDate.now())
            .map(pc -> updatePeriodCondition(pc))
            .orElse(0);
    }

    @Override
    public int updatePeriodCondition(PeriodCondition periodCondition) {
        if (periodCondition == null || periodCondition.getId() == null || periodCondition.getId() <= 0) {
            logger.debug("Empty periodCondition or invalid id");
            return 0;
        }
        int cntUpdPc = periodConditionDao.update(periodCondition);
        logger.debug("cntUpdPc = " + cntUpdPc);
        return cntUpdPc;
    }

    @Override
    public int updatePeriodCondition(List<PeriodCondition> periodConditionList) {
        if (CollectionUtils.isEmpty(periodConditionList)) {
            logger.debug("Empty periodCondition");
            return 0;
        }
        List<PeriodCondition> filteredPeriodConditions = periodConditionList.stream()
            .filter(Objects::nonNull)
            .filter(pc -> Predicates.greaterThanZero(pc.getId()))
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filteredPeriodConditions)) {
            logger.debug("Invalid id");
            return 0;
        }
        int cntUpdPc = periodConditionDao.updateBatch(filteredPeriodConditions);
        logger.debug("cntUpdPc = " + cntUpdPc);
        return cntUpdPc;
    }

    @Override
    public int setActivated(Long tacticId, TacticTypeEnum tacticType, Boolean activated) {
        return TacticDaos.getDao(tacticType).setActivated(tacticId, activated);
    }

    /**
     * 插入周期条件, 不校验参数
     */
    private int insertPeriodCondition(PeriodCondition periodCondition) {
        int cntInsPc = periodConditionDao.insertAndSetId(periodCondition);
        logger.debug("cntInsPc = " + cntInsPc);
        return cntInsPc;
    }

    // /**
    // * 删除周期条件, 不校验参数
    // */
    // private int deletePeriodCondition(Long periodConditionId) {
    // int cntDelPC = periodConditionDao.deleteById(periodConditionId);
    // logger.debug("cntDelPC = " + cntDelPC);
    // return cntDelPC;
    // }

    /**
     * 删除周期条件, 不校验参数
     */
    private int deletePeriodCondition(Long tacticId, TacticTypeEnum tacticType) {
        int cntDelPC = periodConditionDao.deleteByTacticIdAndType(tacticId, tacticType);
        logger.debug("cntDelPC = " + cntDelPC);
        return cntDelPC;
    }

    /**
     * 删除再插入规则, 不校验参数
     */
    private int changeRule(List<Rule> ruleList) {
        List<Long> filteredId = Filters.filterValidId(
            ruleList.parallelStream().map(Rule::getId).collect(Collectors.toList()));
        deleteRule(filteredId);
        return insertRule(ruleList);
    }

    /**
     * 插入规则, 不校验参数
     */
    private int insertRule(List<Rule> ruleList) {
        int cntInsRule = ruleDao.insertAndSetIdBatch(ruleList);
        logger.debug("cntInsRule = " + cntInsRule);
        List<RangeCondition> toInsertRC = new ArrayList<>();
        List<Operation> toInsertOp = new ArrayList<>();
        for (Rule r : ruleList) {
            Long ruleId = r.getId();
            RangeCondition rangeCondition = r.getRangeCondition();
            if (rangeCondition != null) {
                rangeCondition.setRuleId(ruleId);
                toInsertRC.add(rangeCondition);
            }
            Operation operation = r.getOperation();
            if (operation != null) {
                operation.setRuleId(ruleId);
                toInsertOp.add(operation);
            }
        }
        if (CollectionUtils.isNotEmpty(toInsertRC)) {
            int cntInsRC = rangeConditionDao.insertAndSetIdBatch(toInsertRC);
            logger.debug("cntInsRC = " + cntInsRC);
        }
        if (CollectionUtils.isNotEmpty(toInsertOp)) {
            int cntInsOp = operationDao.insertAndSetIdBatch(toInsertOp);
            logger.debug("cntInsOp = " + cntInsOp);
        }
        return cntInsRule;
    }

    /**
     * 删除规则, 及对应条件操作, 使用id, 不校验参数
     */
    private int deleteRule(List<Long> ruleIdList) {
        List<Long> filteredIds = Filters.filterValidId(ruleIdList);
        if (CollectionUtils.isEmpty(filteredIds)) {
            return 0;
        }
        deleteRangeCondition(filteredIds);
        deleteOperation(filteredIds);
        int cntDelRule = ruleDao.deleteByIdListBatch(filteredIds);
        logger.debug("cntDelRule = " + cntDelRule);
        return cntDelRule;
    }

    /**
     * 删除规则, 及对应条件操作, 不校验参数
     */
    private int deleteRule(Long tacticId, TacticTypeEnum tacticType) {
        List<Long> ruleIds = ruleDao.listIdByTacticIdAndType(tacticId, tacticType);
        // 默认数据库中获取的id都是有效的, 不用过滤
        if (CollectionUtils.isEmpty(ruleIds)) {
            return 0;
        }
        deleteRangeCondition(ruleIds);
        deleteOperation(ruleIds);
        int cntDelRule = ruleDao.deleteByTacticIdAndType(tacticId, tacticType);
        logger.debug("cntDelRule = " + cntDelRule);
        return cntDelRule;
    }

    private int deleteRangeCondition(List<Long> filteredRuleIds) {
        int cntDelRC = filteredRuleIds.stream()
            .mapToInt(rid -> rangeConditionDao.deleteByRuleId(rid)).sum();
        logger.debug("cntDelRC = " + cntDelRC);
        return cntDelRC;
    }

    private int deleteOperation(List<Long> filteredRuleIds) {
        int cntDelOp = filteredRuleIds.stream()
            .mapToInt(rid -> operationDao.deleteByRuleId(rid)).sum();
        logger.debug("cntDelOp = " + cntDelOp);
        return cntDelOp;
    }

}
