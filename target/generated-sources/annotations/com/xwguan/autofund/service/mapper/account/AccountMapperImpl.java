package com.xwguan.autofund.service.mapper.account;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:03+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
@Primary
public class AccountMapperImpl extends AccountMapperDecorator implements AccountMapper {

    @Autowired
    @Qualifier("delegate")
    private AccountMapper delegate;

    @Override
    public AssetHistory updateAssetHistory(AssetHistory source, AssetHistory target)  {
        return delegate.updateAssetHistory( source, target );
    }

    @Override
    public AssetHistory updateAssetHistoryIgnoreId(AssetHistory source, AssetHistory target)  {
        return delegate.updateAssetHistoryIgnoreId( source, target );
    }

    @Override
    public TradeDetail updateTradeDetail(TradeDetail source, TradeDetail target)  {
        return delegate.updateTradeDetail( source, target );
    }

    @Override
    public TradeDetail updateTradeDetailIgnoreId(TradeDetail source, TradeDetail target)  {
        return delegate.updateTradeDetailIgnoreId( source, target );
    }
}
