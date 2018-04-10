package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.stock.StockDataDao;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyIndexChangeTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.IndexChangeTacticMapper;
import com.xwguan.autofund.util.MathUtils;

@Component
@Scope("prototype")
public class IndexChangeTacticHandler extends AbstractPositionTacticHandler {

    @Autowired
    private StockDataDao stockDataDao;

    @Autowired
    private IndexChangeTacticMapper mapper;
    
    @Autowired
    private CleanCopyIndexChangeTacticMapper cleanCopyMapper;

    public IndexChangeTacticHandler() {
        super();
    }

    @Override
    public IndexChangeTacticHandler handle(PositionTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> endClose = getIndexValue(date);
        if (!endClose.isPresent()) {
            return Optional.empty(); // 该日期不是交易日
        }
        Optional<Double> startClose = getCloseTradeDaysBefore(date);
        if (!startClose.isPresent()) {
            return Optional.empty(); // 该日期之前的数据不足设定交易日数
        }
        double changePct = getChangePct(startClose.get(), endClose.get());
        return getRuleMetCondition(date, changePct);
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> endClose = getIndexValue(date);
        if (!endClose.isPresent()) {
            return Optional.empty(); // 该日期不是交易日
        }
        Optional<Double> startClose = getCloseTradeDaysBefore(date);
        if (!startClose.isPresent()) {
            return Optional.empty(); // 该日期之前的数据不足设定交易日数
        }
        double changePct = getChangePct(startClose.get(), endClose.get());
        Optional<Rule> rule = getRuleMetCondition(date, changePct);
        if (!rule.isPresent()) {
            return Optional.empty(); // 没有满足条件的规则
        }
        return Optional.ofNullable(mapper.toActivatedIndexChangeTactic(getTactic(), rule.get(),
            startClose.get(), endClose.get(), changePct));
    }

    /**
     * 获取某日之前第n个交易日的收盘价, n值为IndexChangeTactic的inTradeDays属性
     */
    private Optional<Double> getCloseTradeDaysBefore(LocalDate date) {
        Integer inTradeDays = getTactic().getInTradeDays();
        Integer indexId = getPosition().getRefIndexId();
        StockData stockData = stockDataDao.getStockDataTradeDaysBefore(indexId, date, inTradeDays);
        return Optional.ofNullable(stockData).map(sd -> sd.getClose());
    }

    private double getChangePct(double start, double end) {
        return MathUtils.deviationRate(end, start, true);
    }

    @Override
    public IndexChangeTactic getTactic() {
        return (IndexChangeTactic) super.getTactic();
    }

    @Override
    public IndexChangeTactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }
}
