package com.xwguan.autofund.dao.account;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.TradeStateEnum;

/**
 * 交易细节dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public interface TradeDetailDao extends BaseDao<TradeDetail>, CUDBatchDao<TradeDetail> {

    TradeDetail getByAccountIdAndDate(@Param("accountId") Long accountId, @Param("date") LocalDate date);

    TradeDetail getLatestByAccountId(@Param("accountId") Long accountId);

    List<TradeDetail> listByAccountId(@Param("accountId") Long accountId, @Param("page") Page page);
    
    TradeDetail getPrevTradeDetailByAccountIdAndDate(@Param("accountId") Long accountId, @Param("date") LocalDate date);

    int deleteByAccountId(@Param("accountId") Long accountId);
    
    int updateTradeState(@Param("id") Long id, @Param("tradeState") TradeStateEnum tradeState);
}
