package com.xwguan.autofund.service.handler.plan;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TradeUnitEnum;
import com.xwguan.autofund.exception.handler.EntitiesNotMatchException;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.handler.tactic.PlanTacticHandler;
import com.xwguan.autofund.service.handler.tactic.PositionTacticHandler;
import com.xwguan.autofund.service.mapper.plan.CleanCopyPlanMapper;
import com.xwguan.autofund.service.mapper.scheme.PlanTradeSchemeMapper;
import com.xwguan.autofund.service.mapper.scheme.PositionTradeSchemeMapper;
import com.xwguan.autofund.util.MathUtils;

/**
 * 默认的计划处理者, 主要用于根据计划生成交易方案和交易相关值计算
 * <p>connDbForData(默认true)的值为
 * <ul>
 * <li><b>true</b> plan中引用其他实体类的属性可以没有值, 会从数据库中获取
 * <li><b>false</b> plan必须为完整对象, 否则NPE
 * </ul>
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-18
 */
@Component
@Scope("prototype")
public class PlanHandler extends AbstractEntityHandler<Plan> implements CleanlyCopyable<Plan> {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 所有输出值均保留两位小数
     */
    private static int ROUND_SCALE = 2;

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private PositionTradeSchemeMapper positionSchemeMapper;

    @Autowired
    private PlanTradeSchemeMapper planSchemeMapper;

    @Autowired
    private CleanCopyPlanMapper cleanCopyPlanMapper;

    @Autowired
    private AccountHandler accountHandler;

    @Autowired
    private PlanAssembler planAssembler;

    /**
     * 在使用plan中的List, 而List没有数据时, 是否尝试从数据库中获取, 默认为true
     */
    private boolean connDbForData = true;

    /**
     * 处理计划
     */
    @Override
    public PlanHandler handle(Plan plan) {
        super.handle(plan);
        return this;
    }

    public PlanHandler setConnDbForData(boolean connDbForData) {
        this.connDbForData = connDbForData;
        accountHandler.setConnDbForData(connDbForData);
        return this;
    }

    /**
     * 生成交易方案, 若传入日期不是交易日则返回空
     * 
     * @param tradeDate 方案日期
     * @return 交易方案
     * @throws IOException 获取实时值网络异常
     */
    public Optional<PlanTradeScheme> generateTradeScheme(LocalDate tradeDate) throws IOException {
        if (needNotHandle(tradeDate)) {
            return Optional.empty();
        }
        List<ActivatedTactic> activatedPlanTacticList = getActivatedPlanTacticList(tradeDate); // 获取激活的计划策略
        List<PositionTradeScheme> positionSchemeList = CollectionUtils.isNotEmpty(activatedPlanTacticList)
            // activatedPlanTacticList非空, 即存在激活的计划策略, 按计划策略生成持仓交易方案, 不应用持仓策略
            ? getPositionTradeSchemeList(activatedPlanTacticList, tradeDate)
            // 不存在激活的计划策略, 对每个持仓应用持仓策略生成持仓交易方案
            : getPositionTradeSchemeList(tradeDate);
        double totalBuy = 0; // 买入金额求和
        for (PositionTradeScheme positionScheme : positionSchemeList) {
            Double tradeValue = positionScheme.getTradeValue();
            totalBuy += tradeValue > 0 ? tradeValue : 0; // tradeValue > 0表示买入金额
        }
        PlanTradeScheme planTradeScheme = planSchemeMapper.toPlanTradeScheme(getPlan(), activatedPlanTacticList,
            positionSchemeList, tradeDate, totalBuy);
        if (planTradeScheme.getTotalBuy() > 0) {
            planTradeScheme.getPositionTradeSchemeList().stream()
                .map(PositionTradeScheme::getActivatedTacticList)
                .forEach(System.out::println);
        }
        return Optional.of(planTradeScheme);
    }

    /**
     * 优先级最高的计划策略被激活时, 持仓策略不应用, 直接按计划策略生成持仓交易方案列表
     * <p><b>默认AssetHistoryList已经按时间顺序排列或只有所需的数据</b>
     * 
     * @param activatedPlanTacticList 激活的计划策略列表
     * @param tradeDate 交易日日期
     * @return
     */
    private List<PositionTradeScheme> getPositionTradeSchemeList(List<ActivatedTactic> activatedPlanTacticList,
        LocalDate tradeDate) {
        List<PositionTradeScheme> positionSchemeList = new ArrayList<>();
        double tradeValuePct = calcPlanTacticTradeValuePct(activatedPlanTacticList); // 单位为百分比
        for (Position position : getPositionList()) {
            double tradeValue = calcPlanTacticTradeValue(position, tradeValuePct, tradeDate);
            PositionTradeScheme positionScheme = positionSchemeMapper.toPositionTradeScheme(position, tradeValue,
                activatedPlanTacticList, tradeDate);
            positionSchemeList.add(positionScheme);
        }
        return positionSchemeList;
    }

    /**
     * 没有计划策略被激活时, 对每个持仓计算总操作, 并生成持仓交易方案列表.
     * 若某个持仓没有激活的持仓计划, 该持仓不生成持仓交易方案
     * 
     * @param tradeDate 交易日日期
     * @return 持仓交易方案列表, 可能为空
     * @throws IOException 获取实时值网络异常
     */
    private List<PositionTradeScheme> getPositionTradeSchemeList(LocalDate tradeDate) throws IOException {
        List<PositionTradeScheme> positionSchemeList = new ArrayList<>();
        for (Position position : getPositionList()) {
            List<ActivatedTactic> activatedPositionTacticList = getActivatedPositionTacticList(tradeDate, position);
            if (activatedPositionTacticList.isEmpty()) {
                continue;
            }
            double tradeValue = calcPositionTacticTradeValue(position, activatedPositionTacticList, tradeDate);
            PositionTradeScheme positionScheme = positionSchemeMapper.toPositionTradeScheme(position, tradeValue,
                activatedPositionTacticList, tradeDate);
            positionSchemeList.add(positionScheme);
        }
        return positionSchemeList;
    }

    /**
     * 获取激活的计划策略列表, 可能为空列表
     */
    private List<ActivatedTactic> getActivatedPlanTacticList(LocalDate tradeDate)
        throws IOException {
        List<ActivatedTactic> activatedTacticList = new ArrayList<>();
        for (PlanTactic tactic : getPlanTacticList()) {
            tactic.handler().handle(getPlan()).getActivatedTactic(tradeDate)
                .ifPresent(at -> activatedTacticList.add(at));
        }
        return activatedTacticList;
    }

    /**
     * 获取激活的持仓策略列表, 可能为空列表
     */
    private List<ActivatedTactic> getActivatedPositionTacticList(LocalDate tradeDate, Position position)
        throws IOException {
        List<ActivatedTactic> activatedTacticList = new ArrayList<>();
        for (PositionTactic tactic : getPositionTacticList()) {
            try {
                tactic.handler().handle(position).getActivatedTactic(tradeDate)
                    .ifPresent(at -> activatedTacticList.add(at));
            } catch (EntitiesNotMatchException e) {
                e.printStackTrace();
            }
        }
        return activatedTacticList;
    }

    /**
     * 计算计划策略的总交易值, 单位是百分比
     * <p>不验证activatedPlanTacticList中的策略是否为计划策略
     * 
     * @param activatedPlanTacticList 激活的计划策略列表
     * @return
     */
    private double calcPlanTacticTradeValuePct(List<ActivatedTactic> activatedPlanTacticList) {
        if (CollectionUtils.isEmpty(activatedPlanTacticList)) {
            return 0; // 没有激活的计划策略
        }
        double tradeSumPct = 0;
        for (ActivatedTactic at : activatedPlanTacticList) {
            Operation operation = at.getOperation();
            if (!TradeUnitEnum.PERCENT.equals(operation.getUnit())) {
                continue; // 交易单位不是百分比则跳过, 其他单位没有实际意义
            }
            tradeSumPct += operation.getTradeValue();
            if (tradeSumPct <= -100) {
                return -100; // 卖出超过100%时, 返回-100%, 表示全部卖出, 买入可以超过100%
            }
        }
        return tradeSumPct;
    }

    /**
     * 计算某持仓的计划策略交易值. 返回值大于0为买入, 单位是元; 小于0为卖出, 单位是份额; 等于0不操作
     * <p><b>默认持仓的账户的assetHistoryList已经按时间顺序排列或只有所需的数据</b>
     * 
     * @param position 持仓, 持仓所属账户的AssetHistoryList应该已经按时间顺序排列或只有所需的数据
     * @param tradeValuePct 计划策略的总交易值, 单位是百分比
     * @return
     */
    private double calcPlanTacticTradeValue(Position position, double tradeValuePct, LocalDate tradeDate) {
        // 获取前一交易日的持仓历史, 若不存在则从模板中获取默认的PositionHistory
        AssetHistory prevHistory = position.getAccount().handler().setConnDbForData(connDbForData)
            .getPrevAssetHistoryOrDefault(tradeDate);
        // tradeValuePct < 0, 卖出给定比例的份额; tradeValuePct > 0, 买入给定比例的金额
        double tradeValue = tradeValuePct < 0
            ? MathUtils.pctToDecimal(prevHistory.getPosShare() * tradeValuePct, ROUND_SCALE)
            : MathUtils.pctToDecimal(prevHistory.getPosAsset() * tradeValuePct, ROUND_SCALE);
        return tradeValue;
    }

    /**
     * 计算某持仓的持仓策略的总交易值, 返回值大于0为买入, 单位是元; 小于0为卖出, 单位是份额; 没有激活的持仓策略时, 交易值为0, 表示不操作
     * <p><b>不验证activatedPlanTacticList中的策略是否为计划策略</b>
     * <p><b>默认持仓的账户的assetHistoryList已经按时间顺序排列或只有所需的数据</b>
     * 
     * @param position 持仓, 持仓所属账户的AssetHistoryList应该已经按时间顺序排列或只有所需的数据
     * @param activatedPosTacticList 激活的持仓策略列表, null表示没有策略被激活
     * @return
     */
    private double calcPositionTacticTradeValue(Position position, List<ActivatedTactic> activatedPosTacticList,
        LocalDate tradeDate) {
        if (CollectionUtils.isEmpty(activatedPosTacticList)) {
            return 0; // 没有激活的持仓策略
        }
        double tradeSumYuan = 0; // 单位先统一为元, 方便比较
        // 获取前一交易日的持仓历史, 若不存在则从模板中获取默认的PositionHistory
        AssetHistory prevHistory = position.getAccount().handler().setConnDbForData(connDbForData)
            .getPrevAssetHistoryOrDefault(tradeDate);
        double prevPosAsset = prevHistory.getPosAsset();
        double prevPosShare = prevHistory.getPosShare();
        double prevNav = prevPosAsset / prevPosShare; // 有点误差可以接受, 对交易值的影响在千分之一量级, 没必要从数据库中取准确值
        // double nav = fundHistoryDao.getFundHistoryByFundIdAndDate(position.getFundId(), date).getNav();
        for (ActivatedTactic at : activatedPosTacticList) {
            Operation operation = at.getOperation();
            double tradeValue = operation.getTradeValue();
            switch (operation.getUnit()) {
            case PERCENT:
                tradeSumYuan += MathUtils.pctToDecimal(prevPosAsset * tradeValue, ROUND_SCALE); // 百分比乘总资产换算成元
                break;
            case SHARE:
                tradeSumYuan += round(prevNav * tradeValue); // 份额乘净值换算成元
                break;
            case YUAN:
                tradeSumYuan += tradeValue;
                break;
            default:
                continue;
            }
        }
        return tradeSumYuan > 0 ? tradeSumYuan : round(tradeSumYuan / prevNav); // 交易值为负换算成份额
    }

    /**
     * 复制Plan并设置为初始状态, id为-1, 历史列表为空List, 周期条件按参数initDate重置
     */
    public Plan cleanCopyAndReset(LocalDate initDate) {
        Plan copiedPlan = cleanCopy();
        resetPeriodCondition(copiedPlan.getPlanTacticList(), initDate);
        resetPeriodCondition(copiedPlan.getPositionTacticList(), initDate);
        return copiedPlan;
    }

    @Override
    public Plan cleanCopy() {
        Plan copiedPlan = cleanCopyPlanMapper.cleanCopy(getPlan());
        List<PlanTactic> copiedPlanTactics = getPlan().getPlanTacticList().stream()
            .map(PlanTactic::handler)
            .map(PlanTacticHandler::cleanCopy)
            .collect(Collectors.toList());
        copiedPlan.setPlanTacticList(copiedPlanTactics);
        List<PositionTactic> copiedPositionTactics = getPlan().getPositionTacticList().stream()
            .map(PositionTactic::handler)
            .map(PositionTacticHandler::cleanCopy)
            .collect(Collectors.toList());
        copiedPlan.setPositionTacticList(copiedPositionTactics);
        return copiedPlan;
    }

    private void resetPeriodCondition(List<? extends Tactic> tacticList, LocalDate initDate) {
        tacticList.stream().map(Tactic::getPeriodCondition)
            .forEach(pc -> pc.handler().resetAndClearPeriodCondition(initDate));
    }

    /**
     * 不是交易日不处理
     * 
     * @param date
     * @return
     */
    public boolean needNotHandle(LocalDate date) {
        return dateTimeService.isNotTradeDay(date);
    }

    public Plan getPlan() {
        return getEntity();
    }

    /**
     * 保留两位小数
     */
    private double round(double num) {
        return MathUtils.round(num, ROUND_SCALE);
    }

    /**
     * 获取不带账户历史的持仓列表, 在列表为空且connDbForData为true时尝试从数据库获取. planId小于0时(不是数据库的有效id)不连接数据库
     */
    public List<Position> getPositionList() {
        if (connDbForData) {
            planAssembler.assemblePositions(getPlan());
        }
        return getPlan().getPositionList();
    }

    /**
     * 获取不带账户历史的账户对象, 在账户对象为null且connDbForData为true时试从数据库获取. planId小于0时(不是数据库的有效id)不连接数据库
     */
    public Account getAccount() {
        if (connDbForData) {
            planAssembler.assembleAccount(getPlan());
        }
        return getPlan().getAccount();
    }

    /**
     * 获取计划策略列表, 在列表为null且connDbForData为true时试从数据库获取. planId小于0时(不是数据库的有效id)不连接数据库
     */
    public List<PlanTactic> getPlanTacticList() {
        if (connDbForData) {
            planAssembler.assemblePlanTactics(getPlan());
        }
        return getPlan().getPlanTacticList();
    }

    /**
     * 获取持仓策略列表, 在列表为null且connDbForData为true时试从数据库获取. planId小于0时(不是数据库的有效id)不连接数据库
     */
    public List<PositionTactic> getPositionTacticList() {
        if (connDbForData) {
            planAssembler.assemblePositionTactics(getPlan());
        }
        return getPlan().getPositionTacticList();
    }

    /**
     * 从Plan对象中获取PlanTactic和PositionTactic的总列表
     */
    public List<Tactic> getAllTactics() {
        List<Tactic> allTactics = new ArrayList<>();
        allTactics.addAll(getPlanTacticList());
        allTactics.addAll(getPositionTacticList());
        return allTactics;
    }

}
