package com.xwguan.autofund.service.api;

import java.util.List;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;

/**
 * 持仓服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-17
 */
public interface PositionService {

    /**
     * 不带账户历史的资产不为0的持仓
     * 
     * @param id
     * @return
     */
    Position getPositionById(Long id);

    /**
     * 计划id对应的资产不为0的持仓列表, 不带账户历史
     * 
     * @param planId
     * @param includeLatestAccountHistory
     * @param page
     * @return
     */
    List<Position> listByPlanId(Long planId, Boolean includeLatestAccountHistory, Page page);

    /**
     * 获取持仓模板, 其中计划id为-1
     * 
     * @param fundCode 基金代码
     * @param refIndexSymbol 参考指数代码
     * @return 对应的持仓模板
     * @throws InvalidTickerSymbolException
     * @throws InvalidFundCodeException
     */
    Position getPositionTemplate(String fundCode, String refIndexSymbol)
        throws InvalidFundCodeException, InvalidTickerSymbolException;

    /**
     * 插入持仓
     * 
     * @param position
     * @return
     */
    int insertPosition(Position position);

    /**
     * 插入持仓
     * 
     * @param positionList
     * @return
     */
    int insertPosition(List<Position> positionList);

    // /**
    // * 删除id对应的持仓
    // *
    // * @param id
    // * @return
    // */
    // int deletePosition(Long id);

    // /**
    // * 删除idList对应的持仓
    // *
    // * @param idList
    // * @return
    // */
    // int deletePosition(List<Long> idList);

    /**
     * 删除planId对应的持持仓.
     * 一般情况下, 持仓不能被用户主动删除, 只有在删除计划时, 会将计划所属的持仓一同删除
     * 
     * @param planId
     * @return
     */
    int deletePositionOfPlan(Long planId);

    /**
     * 改变参考指数
     * 
     * @param symbol 指数代码
     * @return
     * @throws InvalidTickerSymbolException 指数代码无效 
     */
    int changeRefIndex(Long id, String symbol) throws InvalidTickerSymbolException;

}
