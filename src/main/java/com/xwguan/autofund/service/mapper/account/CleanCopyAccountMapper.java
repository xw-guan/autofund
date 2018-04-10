package com.xwguan.autofund.service.mapper.account;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;

@Mapper
public interface CleanCopyAccountMapper {

    @Mappings({
        @Mapping(target = "id", constant = "-1"),
        @Mapping(target = "ownerId", constant = "-1"),
    })
    Account cleanCopy(Account source);

    default List<TradeDetail> emptyTradeDetailList(List<TradeDetail> list) {
        return new ArrayList<>();
    }

    default List<AssetHistory> emptyAssetHistoryList(List<AssetHistory> list) {
        return new ArrayList<>();
    }

}
