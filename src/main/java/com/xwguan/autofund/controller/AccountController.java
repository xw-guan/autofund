package com.xwguan.autofund.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.dto.common.Result;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.service.api.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    /**
     * url like
     * <code>
     * /account/1/assetHistory?target=1&item=10
     * </code>
     * 
     * @param fundCode 基金代码, eg. 000001
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(value = "/{accountId}/assetHistory", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<AssetHistory>> getAssetHistory(
        @PathVariable("accountId") Long accountId,
        @RequestParam(value = "target", defaultValue = "1") Integer target,
        @RequestParam(value = "item", defaultValue = "10") Integer item) {

        Page page = new Page(target, item);
        try {
            List<AssetHistory> assetHistories = accountService.listAssetHistory(accountId, page);
            return new Result<>(true, assetHistories);
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "获取失败");
        }
    }
    
    /**
     * url like
     * <code>
     * /account/1/tradeDetail?target=1&item=10
     * </code>
     * 
     * @param fundCode 基金代码, eg. 000001
     * @param refIndexSymbol 参考指数代码, eg. 000001.SH
     * @return
     */
    @RequestMapping(value = "/{accountId}/tradeDetail", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TradeDetail>> getTradeDetail(
        @PathVariable("accountId") Long accountId,
        @RequestParam(value = "target", defaultValue = "1") Integer target,
        @RequestParam(value = "item", defaultValue = "10") Integer item) {
        
        Page page = new Page(target, item);
        try {
            List<TradeDetail> tradeDetails = accountService.listTradeDetail(accountId, page);
            return new Result<>(true, tradeDetails);
        } catch (Exception e) {
            logger.error(e.toString() + ", " + e.getMessage());
            return new Result<>(false, "获取失败");
        }
    }
}
