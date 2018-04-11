package com.xwguan.autofund.service.template.plan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.dao.fund.FundDao;
import com.xwguan.autofund.dao.stock.StockDao;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.SymbolFormatEnum;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.template.account.AccountTemplate;
import com.xwguan.autofund.util.CodeUtils;

/**
 * 持仓模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-09
 */
@Component
public class PositionTemplate {

    @Autowired
    private FundDao fundDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private AccountTemplate accountTemplate;

    /**
     * 南方中证量化增强C("002907"), 中证500指数("000905.SH")
     */
    public Position csi500() {
        try {
            return of("002907", "000905.SH");
        } catch (InvalidFundCodeException | InvalidTickerSymbolException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 博时沪深300指数C("002385"), 沪深300指数("000300.SH")
     */
    public Position csi300() {
        try {
            return of("002385", "000300.SH");
        } catch (InvalidFundCodeException | InvalidTickerSymbolException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 南方中证量化增强C("002907"), 中证500指数("000905.SH")<br>
     * 博时沪深300指数C("002385"), 沪深300指数("000300.SH")
     */
    public List<Position> csi300And500() {
        List<Position> positionList = new ArrayList<>();
        positionList.add(csi300());
        positionList.add(csi500());
        return positionList;
    }

    public Position of(String fundCode, String refIndexSymbol)
        throws InvalidFundCodeException, InvalidTickerSymbolException {
        if (!CodeUtils.isFundCode(fundCode)) {
            throw new InvalidFundCodeException("Invalid fundCode");
        }
        Integer fundId = fundDao.getIdByCode(fundCode);
        if (fundId == null) {
            throw new InvalidFundCodeException("Invalid fundCode");
        }
        if (!CodeUtils.isSymbolMatchFormat(refIndexSymbol, SymbolFormatEnum.AUTOFUND)) {
            throw new InvalidTickerSymbolException("Invalid tickerSymbol");
        }
        Integer refIndexId = stockDao.getIdBySymbol(refIndexSymbol);
        if (refIndexId == null) {
            throw new InvalidTickerSymbolException("Invalid tickerSymbol");
        }
        return of(fundId, refIndexId);
    }

    public Position of(Integer fundId, Integer refIndexId) {
        return new Position(-1L, -1L, fundId, refIndexId,
            accountTemplate.of(AccountOwnerTypeEnum.POSITION));
    }

}
