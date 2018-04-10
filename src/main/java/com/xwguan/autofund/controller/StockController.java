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
import com.xwguan.autofund.dto.stock.LatestStockDto;
import com.xwguan.autofund.dto.stock.StockUpdateState;
import com.xwguan.autofund.entity.stock.Stock;
import com.xwguan.autofund.service.api.StockService;
import com.xwguan.autofund.service.mapper.stock.StockMapper;

/**
 * 提供Stock相关CRUD, 返回封装成Result<>对象的json结果
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-09
 */
@Controller
@RequestMapping("/stock")
public class StockController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StockService stockService;

    @Autowired
    private StockMapper stockMapper;

    @RequestMapping(value = "/{symbolOrName}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<LatestStockDto>> queryStock(@PathVariable("symbolOrName") String symbolOrName) {
        Result<List<LatestStockDto>> result;
        try {
            List<Stock> stocks = stockService.listStockBySymbolOrName(symbolOrName, true, new Page());
            List<LatestStockDto> latestStockDtos = stockMapper.toLatestStockDtos(stocks);
            result = new Result<>(true, latestStockDtos);
            logger.info(result.toString());
        } catch (Exception e) {
            logger.error("查询股票信息失败", e);
            result = new Result<>(false, "查询股票信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result<List<StockUpdateState>> updateStock(
        @RequestParam(value = "useMultiThread", defaultValue = "false") Boolean useMultiThread) {

        Result<List<StockUpdateState>> result;
        try {
            List<StockUpdateState> stateList = stockService.updateStockData(useMultiThread);
            logger.info(stateList.toString());
            result = new Result<>(true, stateList);
        } catch (Exception e) {
            logger.error("更新失败", e);
            result = new Result<>(false, "更新失败");
        }
        return result;
    }

}
