package com.xwguan.autofund.controller;

import java.util.List;
import java.util.Map;

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
import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.enums.UpdateStateEnum;
import com.xwguan.autofund.service.api.FundService;
import com.xwguan.autofund.service.mapper.fund.FundMapper;

/**
 * 提供Fund相关CRUD, 返回封装成Result<>对象的json结果
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-09
 */
@Controller
@RequestMapping("/fund")
public class FundController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FundService fundService;

    @Autowired
    private FundMapper fundMapper;

    @RequestMapping(value = "/{searchField}", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<LatestFundDto>> queryFund(@PathVariable("searchField") String searchField) {
        Result<List<LatestFundDto>> result;
        try {
            List<Fund> funds = fundService.searchFund(searchField, new Page());
            List<LatestFundDto> latestFundDtos = fundMapper.toLatestFundDtos(funds);
            result = new Result<>(true, latestFundDtos);
            logger.info(result.toString());
        } catch (Exception e) {
            logger.error("查询基金信息失败", e);
            result = new Result<>(false, "查询基金信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result<Map<Integer, UpdateStateEnum>> updateFund(
        @RequestParam(value = "useMultiThread", defaultValue = "false") Boolean useMultiThread) {
        Result<Map<Integer, UpdateStateEnum>> result;
        try {
            Map<Integer, UpdateStateEnum> states = fundService.updateFundHistory(useMultiThread);
            logger.debug(states.toString());
            result = new Result<>(true, states);
        } catch (Exception e) {
            logger.error("更新失败", e);
            result = new Result<>(false, "更新失败");
        }
        return result;
    }

}
