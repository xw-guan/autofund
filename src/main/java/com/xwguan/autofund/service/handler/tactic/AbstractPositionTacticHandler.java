package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.xwguan.autofund.dao.fund.FundHistoryDao;
import com.xwguan.autofund.dao.stock.StockDataDao;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.fund.RealTimeFundData;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.entity.stock.RealTimeStockData;
import com.xwguan.autofund.entity.stock.StockData;
import com.xwguan.autofund.exception.handler.EntitiesNotMatchException;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.api.FundService;
import com.xwguan.autofund.service.api.StockService;

/**
 * 应用于持仓的持仓策略处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-18
 */
public abstract class AbstractPositionTacticHandler extends AbstractTacticHandler<PositionTactic> implements PositionTacticHandler {

    /**
     * 持仓
     */
    private Position position;

    @Autowired
    private StockDataDao stockDataDao;

    @Autowired
    private StockService stockService;

    @Autowired
    private FundHistoryDao fundHistoryDao;

    @Autowired
    private FundService fundService;

    @Autowired
    private DateTimeService dateTimeService;

    public AbstractPositionTacticHandler() {
        super();
    }

    @Override
    public PositionTacticHandler handle(PositionTactic tactic, Position position) {
        super.handle(tactic);
        return handle(position);
    }

    @Override
    public PositionTacticHandler handle(Position position) {
        this.position = Objects.requireNonNull(position);
        throwExceptionIfEntitiesNotMatch();
        return this;
    }

    /**
     * 获取指数值, 日期为当日并且调用时间在交易时间内则获取实时值, 否则获取数据库中日期对应的值
     * 
     * @param date 日期
     * @return
     * @throws IOException
     */
    protected Optional<Double> getIndexValue(LocalDate date) throws IOException {
        Integer refIndexId = getPosition().getRefIndexId();
        if (useRealTimeValue(date)) {
            RealTimeStockData realTimeStockData = stockService.getRealTimeStockData(refIndexId);
            return Optional.ofNullable(realTimeStockData).map(rtsd -> rtsd.getPrice());
        } else {
            StockData stockData = stockDataDao.getStockDataByStockIdAndDate(refIndexId, date);
            return Optional.ofNullable(stockData).map(sd -> sd.getClose());
        }
    }

    protected Optional<Double> getAccNav(LocalDate date) throws IOException {
        Integer fundId = getPosition().getFundId();
        if (useRealTimeValue(date)) {
            RealTimeFundData realTimeFundData = fundService.getRealTimeFundData(fundId);
            return Optional.ofNullable(realTimeFundData).map(rtfd -> rtfd.getEstimatedNav());
        } else {
            FundHistory fundHistory = fundHistoryDao.getFundHistoryByFundIdAndDate(fundId, date);
            return Optional.ofNullable(fundHistory).map(fh -> fh.getAccNav());
        }
    }

    protected boolean useRealTimeValue(LocalDate date) {
        boolean isToday = LocalDate.now().equals(date);
        boolean isTradeTime = dateTimeService.isTradeTime(date, LocalTime.now());
        return isToday && isTradeTime;
    }

    /**
     * 判断position和tactic是否不匹配, 即二者所属的计划id是否不相同
     * 
     * @return true: 不匹配, false: 匹配
     */
    protected boolean isPositionTacticNotMatch() {
        return position.getPlanId() == null || getTactic().getPlanId() == null
            || !position.getPlanId().equals(getTactic().getPlanId());
    }

    private void throwExceptionIfEntitiesNotMatch() {
        if (isPositionTacticNotMatch()) {
            throw new EntitiesNotMatchException("planIds of position (" + position.getPlanId() + ") and tactic ("
                + getTactic().getPlanId() + ") not match");
        }
    }

    @Override
    public PositionTactic getTactic() {
        return getEntity();
    }

    @Override
    public Position getPosition() {
        return position;
    }

}
