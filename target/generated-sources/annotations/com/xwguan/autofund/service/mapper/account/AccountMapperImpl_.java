package com.xwguan.autofund.service.mapper.account;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:03+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class AccountMapperImpl_ implements AccountMapper {

    @Override
    public AssetHistory updateAssetHistory(AssetHistory source, AssetHistory target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setAccountId( source.getAccountId() );
        target.setDate( source.getDate() );
        target.setPosAsset( source.getPosAsset() );
        target.setPosIncome( source.getPosIncome() );
        target.setPosIncomeRatePct( source.getPosIncomeRatePct() );
        target.setPosCost( source.getPosCost() );
        target.setPosShare( source.getPosShare() );
        target.setPosPrice( source.getPosPrice() );
        target.setTotalCost( source.getTotalCost() );
        target.setTotalIncome( source.getTotalIncome() );
        target.setTotalIncomeRatePct( source.getTotalIncomeRatePct() );
        target.setTotalHistoryIncome( source.getTotalHistoryIncome() );

        return target;
    }

    @Override
    public AssetHistory updateAssetHistoryIgnoreId(AssetHistory source, AssetHistory target) {
        if ( source == null ) {
            return null;
        }

        target.setAccountId( source.getAccountId() );
        target.setDate( source.getDate() );
        target.setPosAsset( source.getPosAsset() );
        target.setPosIncome( source.getPosIncome() );
        target.setPosIncomeRatePct( source.getPosIncomeRatePct() );
        target.setPosCost( source.getPosCost() );
        target.setPosShare( source.getPosShare() );
        target.setPosPrice( source.getPosPrice() );
        target.setTotalCost( source.getTotalCost() );
        target.setTotalIncome( source.getTotalIncome() );
        target.setTotalIncomeRatePct( source.getTotalIncomeRatePct() );
        target.setTotalHistoryIncome( source.getTotalHistoryIncome() );

        return target;
    }

    @Override
    public TradeDetail updateTradeDetail(TradeDetail source, TradeDetail target) {
        if ( source == null ) {
            return null;
        }

        target.setId( source.getId() );
        target.setAccountId( source.getAccountId() );
        target.setDate( source.getDate() );
        target.setTradeYuan( source.getTradeYuan() );
        target.setTradeShare( source.getTradeShare() );
        target.setBuySum( source.getBuySum() );
        target.setSellSum( source.getSellSum() );
        target.setTradeState( source.getTradeState() );

        return target;
    }

    @Override
    public TradeDetail updateTradeDetailIgnoreId(TradeDetail source, TradeDetail target) {
        if ( source == null ) {
            return null;
        }

        target.setAccountId( source.getAccountId() );
        target.setDate( source.getDate() );
        target.setTradeYuan( source.getTradeYuan() );
        target.setTradeShare( source.getTradeShare() );
        target.setBuySum( source.getBuySum() );
        target.setSellSum( source.getSellSum() );
        target.setTradeState( source.getTradeState() );

        return target;
    }

    @Override
    public LatestAccountDto toLatestAccountDto(Account account) {
        if ( account == null ) {
            return null;
        }

        LatestAccountDto latestAccountDto = new LatestAccountDto();

        latestAccountDto.setId( account.getId() );
        latestAccountDto.setOwnerId( account.getOwnerId() );
        latestAccountDto.setOwnerType( account.getOwnerType() );

        return latestAccountDto;
    }
}
