package com.xwguan.autofund.service.template.account;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.TradeStateEnum;

/**
 * 交易细节模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-06
 */
@Component
public class TradeDetailTemplate {

    /**
     * 字段id, accountId, fundId为-1, tradeState为ZERO_TRADE_VALUE, 其余值均为0的TradeDetail
     * 
     * @param date 日期
     * @param ownerType 所有者类型
     * @return
     */
    public TradeDetail defaultTradeDetail(LocalDate date) {
        return new TradeDetail(-1L, -1L, date, 0D, 0D, 0D, 0D, TradeStateEnum.ZERO_TRADE_VALUE);
    }

}
