package com.xwguan.autofund.service.mapper.account;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;

@Mapper
@DecoratedWith(AccountMapperDecorator.class)
public interface AccountMapper {

    /**
     * 完整更新source到target, 返回target
     */
    AssetHistory updateAssetHistory(AssetHistory source, @MappingTarget AssetHistory target);

    /**
     * 更新source除id外的字段到target, 返回target
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    AssetHistory updateAssetHistoryIgnoreId(AssetHistory source, @MappingTarget AssetHistory target);

    /**
     * 完整更新source到target, 返回target
     */
    TradeDetail updateTradeDetail(TradeDetail source, @MappingTarget TradeDetail target);

    /**
     * 更新source除id外的字段到target, 返回target
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
    })
    TradeDetail updateTradeDetailIgnoreId(TradeDetail source, @MappingTarget TradeDetail target);

    @Mappings({
        @Mapping(target = "id", source = "account.id"),
        @Mapping(target = "latestAssetHistory", ignore = true),
        @Mapping(target = "latestTradeDetail", ignore = true),
    })
    LatestAccountDto toLatestAccountDto(Account account);
    
}
