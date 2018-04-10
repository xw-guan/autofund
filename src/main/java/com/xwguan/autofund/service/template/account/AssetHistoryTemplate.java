package com.xwguan.autofund.service.template.account;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.AssetHistory;

/**
 * 资产历史模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-06
 */
@Component
public class AssetHistoryTemplate {

    /**
     * id, accountId为-1, 所有其他值均为0的AssetHistory
     * 
     * @param date 日期
     * @param ownerType 所有者类型
     * @return 所有值均为0的AssetHistory
     */
    public AssetHistory defaultAssetHistory(LocalDate date) {
        return new AssetHistory(-1L, -1L, date, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D, 0D);
    }

}
