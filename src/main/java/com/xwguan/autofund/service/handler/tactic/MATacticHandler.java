package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.stock.MADao;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.entity.stock.RealTimeMA;
import com.xwguan.autofund.service.api.StockService;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyMATacticMapper;
import com.xwguan.autofund.service.mapper.tactic.MATacticMapper;
import com.xwguan.autofund.util.MathUtils;

@Component
@Scope("prototype")
public class MATacticHandler extends AbstractPositionTacticHandler {

    @Autowired
    private MADao maDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private MATacticMapper mapper;
    
    @Autowired
    private CleanCopyMATacticMapper cleanCopyMapper;

    public MATacticHandler() {
        super();
    }

    @Override
    public MATacticHandler handle(PositionTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> indexValueOption = getIndexValue(date);
        if (!indexValueOption.isPresent()) {
            return Optional.empty(); // 该日期无指数数据
        }
        Optional<Double> maValueOption = getMAValue(date);
        if (!maValueOption.isPresent()) {
            return Optional.empty(); // 该日期无ma数据
        }
        double deviationPct = getDeviationPct(indexValueOption.get(), maValueOption.get());
        return getRuleMetCondition(date, deviationPct);
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) throws IOException {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        Optional<Double> indexValueOption = getIndexValue(date);
        if (!indexValueOption.isPresent()) {
            return Optional.empty(); // 该日期无指数数据
        }
        Optional<Double> maValueOption = getMAValue(date);
        if (!maValueOption.isPresent()) {
            return Optional.empty(); // 该日期无ma数据
        }
        double deviationPct = getDeviationPct(indexValueOption.get(), maValueOption.get());
        Optional<Rule> rule = getRuleMetCondition(date, deviationPct);
        if (!rule.isPresent()) {
            return Optional.empty(); // 没有满足条件的规则
        }
        return Optional.ofNullable(mapper.toActivatedMATactic(getTactic(), rule.get(),
            indexValueOption.get(), maValueOption.get(), deviationPct));
    }

    private Optional<Double> getMAValue(LocalDate date) throws IOException {
        Integer refIndexId = getPosition().getRefIndexId();
        if (useRealTimeValue(date)) {
            RealTimeMA realTimeMA = stockService.getRealTimeMA(refIndexId);
            return Optional.ofNullable(realTimeMA).map(rtma -> rtma.getMAValue(getTactic().getMa()));
        } else {
            MA movingAverage = maDao.getMAByStockIdAndDate(getPosition().getRefIndexId(), date, getTactic().getMa());
            return Optional.ofNullable(movingAverage).map(ma -> ma.getMAValue(getTactic().getMa()));
        }
    }

    private double getDeviationPct(double indexValue, double maValue) {
        return MathUtils.deviationRate(indexValue, maValue, true);
    }

    @Override
    public MATactic getTactic() {
        return (MATactic) super.getTactic();
    }

    @Override
    public MATactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }
}
