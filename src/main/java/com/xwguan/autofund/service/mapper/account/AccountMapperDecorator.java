package com.xwguan.autofund.service.mapper.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.service.util.Comparators;
import com.xwguan.autofund.service.util.Predicates;

/**
 * Account中的列表中取出最新值
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-04-04
 */
public abstract class AccountMapperDecorator implements AccountMapper {
    @Autowired
    @Qualifier("delegate")
    private AccountMapper delegate;

    @Override
    public LatestAccountDto toLatestAccountDto(Account account) {
        LatestAccountDto latestAccountDto = delegate.toLatestAccountDto(account);
        if (latestAccountDto == null) {
            return null;
        }
        AssetHistory latestAssetHistory = account.getAssetHistoryList().parallelStream()
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        TradeDetail latestTradeDetail = account.getTradeDetailList().parallelStream()
            .filter(Predicates::validTradeDetail) // 过滤异常状态
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        latestAccountDto.setLatestAssetHistory(latestAssetHistory);
        latestAccountDto.setLatestTradeDetail(latestTradeDetail);
        return latestAccountDto;
    }

}
