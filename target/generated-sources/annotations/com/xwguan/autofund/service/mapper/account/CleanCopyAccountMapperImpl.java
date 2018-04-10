package com.xwguan.autofund.service.mapper.account;

import com.xwguan.autofund.entity.account.Account;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyAccountMapperImpl implements CleanCopyAccountMapper {

    @Override
    public Account cleanCopy(Account source) {
        if ( source == null ) {
            return null;
        }

        Account account = new Account();

        account.setOwnerType( source.getOwnerType() );
        account.setAssetHistoryList( emptyAssetHistoryList( source.getAssetHistoryList() ) );
        account.setTradeDetailList( emptyTradeDetailList( source.getTradeDetailList() ) );

        account.setOwnerId( Long.parseLong( "-1" ) );
        account.setId( Long.parseLong( "-1" ) );

        return account;
    }
}
