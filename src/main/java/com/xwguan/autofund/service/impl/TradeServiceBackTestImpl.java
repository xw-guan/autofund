package com.xwguan.autofund.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.enums.TradeStateEnum;
import com.xwguan.autofund.exception.trade.TradeException;
import com.xwguan.autofund.service.api.TradeService;

/**
 * 交易服务回测实现
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-25
 */
@Service
public class TradeServiceBackTestImpl extends AbstractTradeServiceImpl implements TradeService {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<TradeDetail> executeTrade(PlanTradeScheme planTradeScheme) {
        return super.executeTrade(planTradeScheme);
    }

    /**
     * 回测直接将交易状态设为成功
     */
    @Override
    public TradeDetail executeTrade(PositionTradeScheme positionTradeScheme) {
        TradeDetail tradeDetail = super.executeTrade(positionTradeScheme);
        tradeDetail.setTradeState(TradeStateEnum.SUCCESS);
        return tradeDetail;
    }

    @Override
    public void executeAfterTrade(PlanTradeScheme planTradeScheme) throws TradeException {
        super.executeAfterTrade(planTradeScheme);
    }


    /**
     * 回测不需要取消交易方法
     */
    @Unimplement
    @Override
    public TradeDetail cancelTrade(Position position, TradeDetail positionTradeDetail) {
        return null;
    }

    /**
     * 回测不需要
     */
    @Unimplement
    @Override
    public void setRealTradeDetail(TradeDetail realPositionTradeDetail) {
    }

}
