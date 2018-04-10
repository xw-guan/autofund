package com.xwguan.autofund.service.api;

import java.util.List;

import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.exception.trade.TradeException;

/**
 * 交易服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-13
 */
public interface TradeService {

    /**
     * 执行交易
     * 
     * @param planTradeScheme 交易方案
     * @return 持仓交易细节列表, 对应Plan的所有持仓, 包含交易状态
     */
    List<TradeDetail> executeTrade(PlanTradeScheme planTradeScheme);
    
    /**
     * 执行交易
     * 
     * @param positionTradeScheme 持仓交易方案
     * @return 持仓交易细节, 包含交易状态
     */
    TradeDetail executeTrade(PositionTradeScheme positionTradeScheme);

    /**
     * 取消交易
     * 
     * @param position 持仓
     * @param positionTradeDetail 交易细节
     * @return 持仓交易细节, 包含交易取消状态
     */
    TradeDetail cancelTrade(Position position, TradeDetail positionTradeDetail);

    /**
     * 交易成功后对计划执行的内容, 包含
     * <ol>
     * <li>计算TradeDetail并放入对应Account中
     * <li>计算AssetHistory并放入对应Account中
     * <li>更新激活策略PeriodCondition和 TODO SuppressCondition状态
     * </ol>
     * 
     * @param planTradeScheme 计划交易方案
     * @throws TradeException 获取净值失败, 超过最大重试次数
     * @return
     */
    void executeAfterTrade(PlanTradeScheme planTradeScheme) throws TradeException;

    /**
     * 设置真实交易细节. 计算值是不考虑交易费用的, 因此需要设置真实交易细节修正
     * 
     * @param realPositionTradeDetail 真实持仓交易细节, 不使用buySum和sellSum
     * @throws TradeException 
     */
    void setRealTradeDetail(TradeDetail realPositionTradeDetail) throws TradeException;

}
