package com.xwguan.autofund.service.template.plan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.PlanExecutionModeEnum;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.template.account.AccountTemplate;

/**
 * 计划模板, 所有持仓均为沪深300和中证500, id, userId为-1
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-26
 */
@Component
public class PlanTemplate {

    @Autowired
    private PositionTemplate positionTemplate;

    @Autowired
    private TacticTemplate tacticTemplate;

    @Autowired
    private AccountTemplate accountTemplate;

    /**
     * 持仓为沪深300和中证500指数基金,
     * 策略见{@link #addMATactic(Plan, LocalDate)}
     */
    public Plan maPlan(LocalDate initDate) {
        Plan plan = csi300And500Plan();
        plan.setName("均线计划");
        addMATactic(plan, initDate);
        return plan;
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 激活日期为当日,
     * 策略见{@link #addMATactic(Plan, LocalDate)}
     */
    public Plan maPlan() {
        return maPlan(LocalDate.now());
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 策略见
     * {@link #addMATactic(Plan, LocalDate)},
     * {@link #addPlanProfitTakingTactic(Plan, LocalDate)},
     * {@link #addPositionProfitTakingTactic(Plan, LocalDate)}
     */
    public Plan maPlanWithProfitTaking(LocalDate initDate) {
        Plan plan = csi300And500Plan();
        plan.setName("止盈均线计划");
        addMATactic(plan, initDate);
        addPlanProfitTakingTactic(plan, initDate);
        addPositionProfitTakingTactic(plan, initDate);
        return plan;
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 激活日期为当日,
     * 策略见
     * {@link #addMATactic(Plan, LocalDate)},
     * {@link #addPlanProfitTakingTactic(Plan, LocalDate)},
     * {@link #addPositionProfitTakingTactic(Plan, LocalDate)}
     */
    public Plan maPlanWithProfitTaking() {
        return maPlanWithProfitTaking(LocalDate.now());
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 策略见{@link #addGainLossTactic(Plan, LocalDate)}
     */
    public Plan gainLossPlan(LocalDate initDate) {
        Plan plan = csi300And500Plan();
        plan.setName("盈亏计划");
        addGainLossTactic(plan, initDate);
        return plan;
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 激活日期为当日,
     * 策略见{@link #addGainLossTactic(Plan, LocalDate)}
     */
    public Plan gainLossPlan() {
        return gainLossPlan(LocalDate.now());
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 策略见{@link #addPeriodBuyTactic(Plan, LocalDate)}
     */
    public Plan periodBuyPlan(LocalDate initDate) {
        Plan plan = csi300And500Plan();
        plan.setName("普通定投计划");
        addPeriodBuyTactic(plan, initDate);
        return plan;
    }

    /**
     * 持仓为沪深300和中证500指数基金,
     * 激活日期为当日,
     * 策略见{@link #addPeriodBuyTactic(Plan, LocalDate)}
     */
    public Plan periodBuyPlan() {
        return periodBuyPlan(LocalDate.now());
    }

    /**
     * 持仓为沪深300和中证500指数基金, 没有策略
     */
    public Plan csi300And500Plan() {
        Plan plan = defaultPlan();
        addPositionCSI300And500(plan);
        return plan;
    }

    /**
     * 默认计划. id, userId为-1, 各列表均为空列表, 其他字段按默认值设置, 均不为null
     * 
     * @return
     */
    public Plan defaultPlan() {
        return new Plan(-1L, -1L, "默认计划", accountTemplate.of(AccountOwnerTypeEnum.PLAN), new ArrayList<>(),
            new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), PlanExecutionModeEnum.IN_APP, true);
    }

    /**
     * Period: monthly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(buy yuan, sell pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-30.0
     * <tr><td>20.0<td>30.0<td>0.0
     * <tr><td>10.0<td>20.0<td>600.0
     * <tr><td>0.0<td>10.0<td>800.0
     * <tr><td>-10.0<td>0.0<td>1000.0
     * <tr><td>-20.0<td>-10.0<td>1200.0
     * <tr><td>-30.0<td>-20.0<td>1400.0
     * <tr><td>-40.0<td>-30.0<td>1600.0
     * <tr><td>-50.0<td>-40.0<td>1800.0
     * <tr><td>-60.0<td>-50.0<td>2000.0
     * <tr><td>-70.0<td>-60.0<td>2200.0
     * <tr><td>-80.0<td>-70.0<td>2400.0
     * <tr><td>-90.0<td>-80.0<td>2600.0
     * <tr><td>-100.0<td>-90.0<td>2800.0
     * <tr><td>-max<td>-100.0<td>3000.0
     * </table>
     */
    public Tactic addMATactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        MATactic tactic = tacticTemplate.maTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: monthly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(buy yuan, sell pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-30.0
     * <tr><td>20.0<td>30.0<td>0.0
     * <tr><td>10.0<td>20.0<td>600.0
     * <tr><td>0.0<td>10.0<td>800.0
     * <tr><td>-10.0<td>0.0<td>1000.0
     * <tr><td>-20.0<td>-10.0<td>1200.0
     * <tr><td>-30.0<td>-20.0<td>1400.0
     * <tr><td>-40.0<td>-30.0<td>1600.0
     * <tr><td>-50.0<td>-40.0<td>1800.0
     * <tr><td>-60.0<td>-50.0<td>2000.0
     * <tr><td>-70.0<td>-60.0<td>2200.0
     * <tr><td>-80.0<td>-70.0<td>2400.0
     * <tr><td>-90.0<td>-80.0<td>2600.0
     * <tr><td>-100.0<td>-90.0<td>2800.0
     * <tr><td>-max<td>-100.0<td>3000.0
     * </table>
     */
    public Tactic addGainLossTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        GainLossTactic tactic = tacticTemplate.gainLossTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: weekly <br>
     * InTradeDays: 22 <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * <tr><td>10.0<td>20.0<td>-10.0
     * </table>
     */
    public Tactic addIndexChangeTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        IndexChangeTactic tactic = tacticTemplate.indexChangeTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: weekly <br>
     * InTradeDays: 22 <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * <tr><td>10.0<td>20.0<td>-10.0
     * </table>
     */
    public Tactic addNavChangeTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        NavChangeTactic tactic = tacticTemplate.navChangeTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: monthly <br>
     * Rules: buy 1000 yuan
     */
    public Tactic addPeriodBuyTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        PeriodBuyTactic tactic = tacticTemplate.periodBuyTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: weekly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-100.0
     * </table>
     */
    public Tactic addPlanProfitTakingTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        ProfitTakingTactic tactic = tacticTemplate.planProfitTakingTactic(initDate);
        plan.getPlanTacticList().add(tactic);
        return tactic;
    }

    /**
     * Period: weekly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * </table>
     */
    public Tactic addPositionProfitTakingTactic(Plan plan, LocalDate initDate) {
        initDate = toNowDateIfNull(initDate);
        GainLossTactic tactic = tacticTemplate.positionProfitTakingTactic(initDate);
        plan.getPositionTacticList().add(tactic);
        return tactic;
    }

    public List<Position> addPositionCSI300And500(Plan plan) {
        List<Position> csi300And500 = positionTemplate.csi300And500();
        plan.getPositionList().addAll(csi300And500);
        return csi300And500;
    }

    public Position addPosition(Plan plan, String fundCode, String refIndexSymbol)
        throws InvalidFundCodeException, InvalidTickerSymbolException {
        Position position = positionTemplate.of(fundCode, refIndexSymbol);
        plan.getPositionList().add(position);
        return position;
    }

    public Position addPosition(Plan plan, Integer fundId, Integer refIndexId) {
        Position position = positionTemplate.of(fundId, refIndexId);
        plan.getPositionList().add(position);
        return position;
    }

    private LocalDate toNowDateIfNull(LocalDate date) {
        return date == null ? LocalDate.now() : date;
    }

}
