package com.xwguan.autofund.service.mapper.plan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.dto.plan.LatestPositionDto;
import com.xwguan.autofund.dto.plan.PositionDto;
import com.xwguan.autofund.dto.stock.LatestStockDto;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.service.api.FundService;
import com.xwguan.autofund.service.api.StockService;
import com.xwguan.autofund.service.mapper.account.AccountMapper;
import com.xwguan.autofund.service.mapper.fund.FundMapper;
import com.xwguan.autofund.service.mapper.stock.StockMapper;
import com.xwguan.autofund.service.template.account.AccountTemplate;

abstract public class PositionMapperDecorator implements PositionMapper {

    @Autowired
    @Qualifier("delegate")
    private PositionMapper delegate;

    @Autowired
    private AccountTemplate accountTemplate;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private FundService fundService;

    @Autowired
    private FundMapper fundMapper;

    @Override
    public Position toPosition(PositionDto positionDto) {
        Position position = delegate.toPosition(positionDto);
        if (position == null) {
            return null;
        }
        Account account = accountTemplate.of(AccountOwnerTypeEnum.POSITION, position.getId());
        position.setAccount(account);
        return position;
    }

    @Override
    public List<Position> toPositionList(List<PositionDto> positionDtoList) {
        if (positionDtoList == null) {
            return null;
        }

        List<Position> list = new ArrayList<Position>(positionDtoList.size());
        for (PositionDto positionDto : positionDtoList) {
            list.add(toPosition(positionDto));
        }

        return list;
    }

    @Override
    public PositionDto toPositionDto(Position position) {
        PositionDto positionDto = delegate.toPositionDto(position);
        if (positionDto == null) {
            return null;
        }
        Integer refIndexId = position.getRefIndexId();
        if (refIndexId != null) {
            Stock refIndex = stockService.getStock(refIndexId);
            if (refIndex != null) {
                positionDto.setRefIndexCode(refIndex.getSymbol());
                positionDto.setRefIndexName(refIndex.getName());
            }
        }
        Integer fundId = position.getFundId();
        if (fundId != null) {
            Fund fund = fundService.getFund(fundId);
            if (fund != null) {
                positionDto.setFundCode(fund.getCode());
                positionDto.setFundName(fund.getName());
            }
        }
        return positionDto;
    }

    @Override
    public List<PositionDto> toPositionDtoList(List<Position> positionList) {
        if (positionList == null) {
            return null;
        }

        List<PositionDto> list = new ArrayList<PositionDto>(positionList.size());
        for (Position position : positionList) {
            list.add(toPositionDto(position));
        }

        return list;
    }

    @Override
    public LatestPositionDto toLatestPositionDto(Position position) {
        LatestPositionDto latestPositionDto = delegate.toLatestPositionDto(position);
        if (latestPositionDto == null) {
            return null;
        }
        LatestAccountDto latestAccountDto = accountMapper.toLatestAccountDto(position.getAccount());
        latestPositionDto.setLatestAccount(latestAccountDto);
        Integer refIndexId = position.getRefIndexId();
        if (refIndexId != null) {
            Stock refIndex = stockService.getStock(refIndexId, HistoryScopeEnum.LATEST, null);
            if (refIndex != null) {
                LatestStockDto latestRefIndexDto = stockMapper.toLatestStockDto(refIndex);
                latestPositionDto.setLatestRefIndex(latestRefIndexDto);
            }
        }
        Integer fundId = position.getFundId();
        if (fundId != null) {
            Fund fund = fundService.getFund(fundId, true, true, null);
            if (fund != null) {
                LatestFundDto latestFundDto = fundMapper.toLatestFundDto(fund);
                latestPositionDto.setLatestFund(latestFundDto);
            }
        }
        return latestPositionDto;
    }

    @Override
    public List<LatestPositionDto> toLatestPositionDtoList(List<Position> positionList) {
        if (positionList == null) {
            return null;
        }

        List<LatestPositionDto> list = new ArrayList<LatestPositionDto>(positionList.size());
        for (Position position : positionList) {
            list.add(toLatestPositionDto(position));
        }

        return list;
    }

}
