package com.xwguan.autofund.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.exception.trade.TradeException;
import com.xwguan.autofund.service.api.TradeService;

/**
 * 交易服务模拟交易实现
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-25
 */
@Service
public class TradeServiceSimulationTradeImpl extends AbstractTradeServiceImpl implements TradeService {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional
    public List<TradeDetail> executeTrade(PlanTradeScheme planTradeScheme) {
        return super.executeTrade(planTradeScheme);
    }

    @Override
    @Transactional
    public TradeDetail executeTrade(PositionTradeScheme positionTradeScheme) {
        boolean connDb = true;
        return super.executeTrade(positionTradeScheme, connDb);
    }

    @Override
    @Transactional
    public void executeAfterTrade(PlanTradeScheme planTradeScheme) throws TradeException {
        boolean connDb = true;
        super.executeAfterTrade(planTradeScheme, connDb);
    }

    /**
     * TODO 可能有问题, 暂时不要用
     */
    @Override
    @Transactional
    public TradeDetail cancelTrade(Position position, TradeDetail positionTradeDetail) {
        boolean connDb = true;
        return super.cancelTrade(position, positionTradeDetail, connDb);
    }

    /**
     * 设置真实交易细节, 并据此更新后续资产历史和交易细节
     * 问题: 如何保证本方法是在executeAfterTrade之后执行? 现在的实现是默认在executeAfterTrade后执行
     * 
     * @throws TradeException
     */
    @Override
    @Transactional
    public void setRealTradeDetail(TradeDetail realPositionTradeDetail) throws TradeException {
        boolean updateDb = true;
        super.setRealTradeDetail(realPositionTradeDetail, updateDb);
    }

}
