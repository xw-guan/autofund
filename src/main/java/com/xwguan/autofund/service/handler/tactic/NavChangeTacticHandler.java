package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.fund.FundHistoryDao;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyNavChangeTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.NavChangeTacticMapper;
import com.xwguan.autofund.util.MathUtils;

@Component
@Scope("prototype")
public class NavChangeTacticHandler extends AbstractPositionTacticHandler {

    @Autowired
    private FundHistoryDao fundHistoryDao;

    @Autowired
    private NavChangeTacticMapper mapper;
    
    @Autowired
    private CleanCopyNavChangeTacticMapper cleanCopyMapper;

    public NavChangeTacticHandler() {
        super();
    }

    @Override
    public NavChangeTacticHandler handle(PositionTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> startAccNav = getAccNav(date);
        if (!startAccNav.isPresent()) {
            return Optional.empty();
        }
        Optional<Double> endAccNav = getAccNavTradeDaysBefore(date);
        if (!endAccNav.isPresent()) {
            return Optional.empty();
        }
        double changePct = getChangePct(startAccNav.get(), endAccNav.get());
        return getRuleMetCondition(date, changePct);
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> startAccNav = getAccNav(date);
        if (!startAccNav.isPresent()) {
            return Optional.empty(); // 该日期不是交易日
        }
        Optional<Double> endAccNav = getAccNavTradeDaysBefore(date);
        if (!endAccNav.isPresent()) {
            return Optional.empty(); // 该日期之前的数据不足设定交易日数
        }
        double changePct = getChangePct(startAccNav.get(), endAccNav.get());
        Optional<Rule> rule = getRuleMetCondition(date, changePct);
        if (!rule.isPresent()) {
            return Optional.empty(); // 没有满足条件的规则
        }
        return Optional.ofNullable(mapper.toActivatedNavChangeTactic(getTactic(), rule.get(),
            startAccNav.get(), endAccNav.get(), changePct));
    }

    /**
     * 获取某日的累积净值, n值为IndexChangeTactic的inTradeDays字段
     */
    private Optional<Double> getAccNavTradeDaysBefore(LocalDate date) {
        Integer inTradeDays = getTactic().getInTradeDays();
        Integer fundId = getPosition().getFundId();
        FundHistory fundHistory = fundHistoryDao.getFundHistoryDaysBefore(fundId, date, inTradeDays);
        return Optional.ofNullable(fundHistory).map(fh -> fh.getAccNav());
    }

    private double getChangePct(double start, double end) {
        return MathUtils.deviationRate(end, start, true);
    }

    @Override
    public NavChangeTactic getTactic() {
        return (NavChangeTactic) super.getTactic();
    }

    @Override
    public NavChangeTactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }
}
