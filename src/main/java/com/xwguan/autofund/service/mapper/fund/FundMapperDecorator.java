package com.xwguan.autofund.service.mapper.fund;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.service.util.Comparators;

public abstract class FundMapperDecorator implements FundMapper {

    @Autowired
    @Qualifier("delegate")
    private FundMapper delegate;

    @Override
    public LatestFundDto toLatestFundDto(Fund fund) {
        LatestFundDto latestFundDto = delegate.toLatestFundDto(fund);
        if (latestFundDto == null) {
            return null;
        }
        FundHistory latestFundHistory = fund.getFundHistoryList().parallelStream()
            .max(Comparators.HISTORICAL_DATE_COMPARATOR)
            .orElse(null);
        latestFundDto.setLatestFundHistory(latestFundHistory);
        return latestFundDto;
    }

    @Override
    public List<LatestFundDto> toLatestFundDtos(List<Fund> funds) {
        if (funds == null) {
            return null;
        }
        List<LatestFundDto> list = new ArrayList<LatestFundDto>(funds.size());
        for (Fund fund : funds) {
            list.add(toLatestFundDto(fund));
        }
        return list;
    }
}
