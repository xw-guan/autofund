package com.xwguan.autofund.service.api;

import java.util.List;
import java.util.Map;

import com.xwguan.autofund.dto.stock.StockUpdateState;
import com.xwguan.autofund.enums.UpdateStateEnum;

/**
 * 计划执行服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-26
 */
public interface FutureExecuteService {
    
    Map<Integer, UpdateStateEnum> FundHistoryAutoUpdate();
    
    List<StockUpdateState> StockDataAutoUpdate();
    
    void AfterTradeAutoExecute();
    
    void TradeAutoExecute();
    
    
}
